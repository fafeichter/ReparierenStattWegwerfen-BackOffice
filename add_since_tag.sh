#!/bin/bash

# Script to add @since tag to Java files based on their Git creation date
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

    # Skip untracked files with no Git history
    if [ -z "$creation_date" ]; then
        echo "⊘ SKIP: $file (no Git commit history found)"
        ((skipped++))
        continue
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