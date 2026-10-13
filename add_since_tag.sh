#!/bin/bash

# Script to add @since tag to Java files based on Git history or file system creation date
# Usage: ./add_since_tag.sh /path/to/project

if [ $# -eq 0 ]; then
    echo "Usage: $0 /path/to/project"
    echo "Example: $0 ~/myproject"
    exit 1
fi

PROJECT_PATH="$1"

if [ ! -d "$PROJECT_PATH" ]; then
    echo "Error: Directory '$PROJECT_PATH' does not exist"
    exit 1
fi

echo "Processing Java files in: $PROJECT_PATH"
echo "---"

total=0
modified=0
skipped=0

cd "$PROJECT_PATH" || exit 1

# Find all .java files while ignoring build, out, and IDE directories
while IFS= read -r file; do
    ((total++))

    # Get initial Git commit date for the file
    creation_date=$(git log --follow --format="%ad" --date=format:"%d.%m.%Y" -- "$file" 2>/dev/null | tail -1)

    # Fallback to file system creation date if no Git history is found
    if [ -z "$creation_date" ]; then
        if [[ "$OSTYPE" == "darwin"* ]]; then
            # macOS stat
            file_ts=$(stat -f %B "$file" 2>/dev/null)
            [ -z "$file_ts" ] || [ "$file_ts" -eq 0 ] && file_ts=$(stat -f %m "$file" 2>/dev/null)
            creation_date=$(date -r "$file_ts" "+%d.%m.%Y" 2>/dev/null)
        else
            # Linux stat
            file_ts=$(stat -c %W "$file" 2>/dev/null)
            [ -z "$file_ts" ] || [ "$file_ts" -eq 0 ] || [ "$file_ts" = "-" ] && file_ts=$(stat -c %Y "$file" 2>/dev/null)
            creation_date=$(date -d "@$file_ts" "+%d.%m.%Y" 2>/dev/null)
        fi
    fi

    # Ultimate fallback if file system timestamp extraction fails
    if [ -z "$creation_date" ]; then
        creation_date=$(date "+%d.%m.%Y")
    fi

    # Skip if file already has @since tag anywhere
    if grep -q "@since" "$file"; then
        echo "⊘ SKIP: $file (already has @since tag)"
        ((skipped++))
        continue
    fi

    if grep -q "@author" "$file"; then
        # 1. Has @author tag -> Insert @since right after the first @author occurrence
        perl -0777 -i -pe "s/(^\s*\*\s*@author[^\n]*)/\$1\n * \@since $creation_date/m" "$file"
        echo "✓ MODIFIED: $file (inserted @since after @author)"
        ((modified++))

    else
        # 2. No @author tag -> Insert full header right above class annotations/declaration with proper spacing
        perl -0777 -i -pe "s/\n*((?:^\s*@\w[^\n]*\n)*^\s*(?:public\s+|private\s+|protected\s+|abstract\s+|final\s+)*(?:class|interface|enum)\s+)/\n\n\/**\n * \@author Fabian Feichter\n * \@since $creation_date\n *\/\n\$1/gm" "$file"
        echo "✓ MODIFIED: $file (added header above class declaration)"
        ((modified++))
    fi

done < <(find . \( -name "build" -o -name "generated" -o -name "out" -o -name ".gradle" -o -name ".idea" -o -name ".git" \) -prune -o -type f -name "*.java" -print)

echo "---"
echo "Summary:"
echo "  Total files:    $total"
echo "  Modified:       $modified"
echo "  Skipped:        $skipped"