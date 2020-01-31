# Git Workflow Guide

> This guide will show you how to create your own branch off of `master` in order to create pull requests that will be merged back into `master`.

---

1. Clone the repository

```
git clone https://github.com/ucsb-cs48-w20/gauchoshare.git
```

2. If you already have the repository cloned on your local machine, you need to make sure you are working with the most up-to-date version of `master` before you create your own separate branch. To do so, execute the following Unix commands in the command line (MAKE SURE YOU ARE EXECUTING THE FOLLOWING COMMANDS WHILE INSIDE THE GITHUB PROJECT FOLDER):

```
git fetch
git pull origin master
```

3. Once you have the most up-to-date version of `master`, you are ready to create your own separate branch. To do so, execute the following Unix commands in the command line, replacing BRANCH_NAME with your branch name &mdash the typical naming convention is your **first initial** + **last name** + **/** + **feature name** (e.g. cyang/GitWorkflowGuide): 

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

6. Click on "Compare & pull request" and add a few comments detailing what changes were made in the branch you are trying to merge with master like so:

7. Once that is done, the final step is to click "Create pull request" and if the branch has no conflicts with the base (master) branch, then you are ready to merge the pull request like so:

---

### Other Helpful Git commands

1. If you ever forget which branch you are on, type the following:

`git branch`

Your current branch will have an asterisks next to it's name.