const { writeFileSync } = require('fs');
const { execSync } = require('child_process');

const branch = execSync('git rev-parse --abbrev-ref HEAD').toString().trim();

const commit = execSync('git rev-parse --short HEAD').toString().trim();

const buildInfo = {
  date: new Date(),
  branch,
  commit,
};
const ts = 'export const buildInfo = ' + JSON.stringify(buildInfo, null, 2) + ';';

writeFileSync('src/environments/build.info.production.ts', ts);
