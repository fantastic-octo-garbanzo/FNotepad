# Contributing to FNotepad


Note: rules are made to be broken. Adjust or ignore any/all of these as you see
fit, but be prepared to justify it to your peers.

## Pull Requests


1. `git fetch upstream`
2. `git checkout -b add-so-and-so-feature upstream/master`
3. Add and commit your changes
4. `git push -u origin add-so-and-so-feature`
5. Make a pull request from your feature branch

When you submit your pull request, your commit log should do most of the talking
when it comes to describing your changes and their motivation. In addition to
this, your pull request's comments will ideally include a test plan that the
reviewers can use to (1) demonstrate the problem on master, if applicable and
(2) verify that the problem no longer exists with your changes applied (or that
your new features work correctly). Document all of the edge cases you're aware
of so we can adequately test them - then verify the test plan yourself before
submitting.

## Commit Messages

Please strive to write good commit messages. Here's some guidelines to follow:

#### Commit messages must always be written in English

The first line should be limited to 50 characters and should be a sentence that
completes the thought [When applied, this commit will...] *"Implement
cmd_move"* or *"Fix #742"* or *"Improve performance of arrange_windows on ARM"*
or similar.

The subsequent lines should be separated from the subject line by a single
blank line, and include optional details. In this you can give justification
for the change, [reference Github issues](https://help.github.com/articles/closing-issues-via-commit-messages/),
or explain some of the subtler details of your patch. This is important because
when someone finds a line of code they don't understand later, they can use the
`git blame` command to find out what the author was thinking when they wrote
it. It's also easier to review your pull requests if they're separated into
logical commits that have good commit messages and justify themselves in the
extended commit description.

As a good rule of thumb, anything you might put into the pull request
description on Github is probably fair game for going into the extended commit
message as well.


## Code Review

When your changes are submitted for review, one or more core committers will
look over them. Smaller changes might be merged with little fanfare, but larger
changes will typically see review from several people. Be prepared to receive
some feedback - you may be asked to make changes to your work. Our code review
process is:

1. **Triage** the pull request. Do the commit messages make sense? Is a test
   plan necessary and/or present? Add anyone as reviewers that you think should
   be there (using the relevant GitHub feature, if you have the permissions, or
   with an @mention if necessary).
2. **Review** the code. Look for code style violations, naming convention
   violations, buffer overflows, memory leaks, logic errors, non-portable code
   (including GNU-isms), etc. For significant changes to the public API, loop in
   a couple more people for discussion.
3. **Execute** the test plan, if present.
4. **Merge** the pull request when all reviewers approve.
5. **File** follow-up tickets if appropriate.


## Style Reference

### Brackets

Brackets always go on the same line, including in functions.
Always include brackets for if/while/for, even if it's a single statement.
```c
void function(void) {
	if (condition1) {
		do_thing1();
	}

	if (condition2) {
		do_thing2();
	} else {
		do_thing3();
	}
}
```

### Indentation

Indentations are a single tab.

For long lines that need to be broken, the continuation line should be indented
with an additional tab.
If the line being broken is opening a new block (functions, if, while, etc.),
the continuation line should be indented with two tabs, so they can't be
misread as being part of the block.
```c
really_long_function(argument1, argument2, ...,
	argument3, argument4);

if (condition1 && condition2 && ...
		condition3 && condition4) {
	do_thing();
}
```

Try to break the line in the place which you think is the most appropriate.


### Line Length

Try to keep your lines under 80 columns, but you can go up to 100 if it
improves readability. Don't break lines indiscriminately, try to find nice
breaking points so your code is easy to read.


### Comments

Comment the code in a way that you could understand it if you had not written it.