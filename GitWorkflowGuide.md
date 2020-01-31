# Git Workflow Guide

> This guide will show you how to create your own branch off of `master` in order to create pull requests that will be merged back into `master`.

---

1. Clone the repository

`git clone https://github.com/ucsb-cs48-w20/gauchoshare.git`

2. If you already have the repository cloned on your local machine, you need to make sure you are working with the most up-to-date version of `master` before you create your own separate branch. To do so, execute the following Unix commands in the command line (MAKE SURE YOU ARE EXECUTING THE FOLLOWING COMMANDS WHILE INSIDE THE GITHUB PROJECT FOLDER):

```
git fetch
git pull origin master
```

3. Once you have the most up-to-date version of `master`, you are ready to create your own separate branch. To do so, execute the following Unix commands in the command line, replacing BRANCH_NAME with your branch name &mdash the typical naming convention is your first initial + last name + '/' + feature name (e.g. cyang/GitWorkflowGuide): 

```
git checkout -b BRANCH_NAME
```

4. Now that you are on your separate branch, make whatever changes you intend to make to the codebase, and once you are ready to commit those changes, do the following:

```
git add .
git commit -m "YOUR COMMIT MESSAGE HERE"
git push origin HEAD
```

5. Once you have pushed your changes to your branch, you should see the following appear on the GitHub repository home page: