# Assignment 3: Software Quality Engineering
This is a repository for assignment 3 of the Software Quality Engineering course at the [Ben-Gurion University](https://in.bgu.ac.il/), Israel.

## Assignment Description
In this assignment, we tested an open-source software called [*Moodle*](https://download.moodle.org/releases/latest/).

$$*Moodle Activity Choice* tests the case of which a student changes his choice in the choice activity when the teacher changes the choice activity option to not be updated

## Installation
To install Moodle demo you can find more information in this site: https://download.moodle.org/releases/latest/ 

## What we tested

We tested the choice module that allows students to submit a choice. We chose to test the following user stories: 

*User story:* A teacher changes the option of updating choice in the choice activity.

*Preconditions:* There is a course with a teacher.

*Expected outcome:* The option updated successfully to No.

*User story:* A students alters his choice.

*Preconditions:* There is a course with a choice activity with for choices to choose from.

*Expected outcome:* The student manages to change his choice.
$$

## How we tested
We used two different testing methods:
1. [Cucumber](https://cucumber.io/), a BDD testing framework.
2. [Provengo](https://provengo.tech/), a story-based testing framework.

Each of the testing methods is elaborated in its own directory. 

## Results
Update all README.md files (except for d-e, see Section 1). Specifically, replace all $$*TODO*…$$ according to the instructions inside the $$.

## Detected Bugs
We detected the following bugs:

1. Bug 1: 
   1. General description: The button submit doesn't disappear the student's page upon changing the updated choice to NO.
   2. Steps to reproduce: teacher changes the choice update to No at the same time the student tries to submit his new choice. 
   3. Expected result: to button save no longer visible to the student
   4. Actual result: error message!
   
