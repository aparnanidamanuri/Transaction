======local machine======
* my own computer
* runs locally

=======version control========
* tracks changes in source code
* can revert to earlier versions
* collaborate

=======Centralized Version Control (CVCS)========
* one central server holds the master copy of the code
* multiple developers can check, edit and commit to central server
* but if central server is down, nobody can commit
* merge conflicts are hard to resolve

=======Distributed Version Control (DVCS)========
* every developer will have a copy of the repo
* work is done locally and changes are pushed/pulled to repo remotely
* fast operations
* easy merge, branch


=======Centralized Version Control (CVCS) vs Distributed Version Control (DVCS)========
* Repo Location
    CVCS -> central server
    DVCS -> developer's machine
* Offline work
  CVCS -> not possible
  DVCS -> completely possible
* Speed
  CVCS -> slower
  DVCS -> faster
* History backup
  CVCS -> on server only
  DVCS -> on every clone
* Fault tolerance
  CVCS -> low
  DVCS -> high
* Branching & merging
  CVCS -> hard
  DVCS -> easy and fast

=======Git Concepts========
1. Repository -> project folder that is tracked by git
can be local or on git

2. Commit -> save point in the project
will record a snapshot of project at a point with a message

3. Branch -> to manage change and versions
separate workspace where we can make changes and try new things w/o affecting main project

4. Merge -> combines the changes from multiple branches

5. Clone -> make a local copy of the repo

6. Push -> upload local repository content to remote repo

7. Pull -> get changes from remote repo and merge them locally

8. 

=======Git Workflow========
3 layer workflow

1. Working Directory -> my project folder
files I'm actively working on
any changes made to the files lives in this

2. Staging area(Indexes) -> temp place to prepare changes for commit 
choose which changes to include in next commit
command -> git add ...
not committed yet

3. Commit History -> staged changes
every commit will have message, timestamp, author, unique hash
record changes to history
command -> git commit -m ...

