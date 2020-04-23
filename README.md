**Prioritize** is an app will allow people to prioritize their work, assignments, or tasks based on which tasks have a higher priority and list them accordingly

## Overview


### App Evaluation

- **Category:** Task Manager, Productivity
- **Mobile:** Push notification, Save content on your Google Calendar
- **Story:** Allow people to prioritize their work, assignments, or tasks based on which tasks have a higher priority and list them accordingly
- **Market:**  This app is targeted specifically at students both college-level and high-school. However, it can cater towards any person who needs use of it.
- **Habit:** This app is meant for daily/ weekly use. 
- **Scope:** The scope of the app is a more specialized To-do app with more complex feature to better suit the user.

## Product Spec

### 1. User Stories (Required and Optional)

**Required Must-have Stories**
- [x] User can create a task, each with a Title, Description, Priority number, and Due date.
- [x] App will prioritize the tasks based on Priority Number and Due date.
- [x] User can view a list of priority items.
- [X] User can successfully add and remove items from the priority list.
- [x] User's list of items persisted upon modification and and retrieved properly on app restart.
- [x] User will be notified of upcoming assignments within a specified time frame. 
- [X] User has the ability to sort in multiple ways.


**Optional Nice-to-have Stories**

- [x] Modify layout to make it more user-friendly.
- [x] User's priority tasks will be intuitively filled into their google calendar if they have a google account. 

## Video Walkthrough

Here's a walkthrough of implemented user stories:

<img src='Prioritize_v1.gif' title='Video Walkthrough' width='' alt='Video Walkthrough' />


**Milestone 2 Gif**
<img src='prioritize gif.gif' title='Video Walkthrough' width='240' height='480' alt='Video Walkthrough' />

**Milestone 3 Gif**
<img src='Walkthrough 3.gif' title='Video Walkthrough' width='240' height='480' alt='Video Walkthrough' />

GIFs created with [LiceCap](http://www.cockos.com/licecap/).
### 2. Screen Archetypes

* Task list screen
   * App will prioritize the tasks based on Priority Number and Due date.
   * User can view a list of priority items.
   * User can successfully add and remove items from the priority list.
   * User's list of items persisted upon modification and and retrieved properly on app restart.
* Task creation screen
   * User can create a task, each with a Title, Description, Priority number, and Due date.
   * User's priority tasks will be intuitively filled into their google calendar if they have a google account. 

### 3. Navigation
**Flow Navigation** (Screen to Screen)

* Home Screen
   * Will show list of Tasks
   * Long click on each Task to edit, mark as complete, or delete.
* Edit screen
   * Will be where you edit a task for priority or due date.
* Task Screen
    * Where task will be created to add to home screen
* Action Bar
    * Setting button to change sort technique.
* Settings Screen
    * allow sort to be changed

## Wireframes
![](https://i.imgur.com/eQCGNui.jpg)

### [Bonus] Create digital wireframes
![](https://i.imgur.com/UA1xMJp.png)

### [BONUS] Interactive Prototype
![](https://i.imgur.com/tVged2s.gif)


## Schema 
### Models

   | Property      | Type     | Description |
   | ------------- | -------- | ------------|
   | edTitle       | String   |Unigue name for each task |
   | edDescription | String   |Description of assignment, detailing how long it might take and what you need to do |
   | priorityNum   | Integer  | Number to tell how important task is  |
   | calendarDate  | Date     | Date at which the task needs to be completed|
   | id            | Number   | Primary Key(for if we do Google Calendar) |
   |     |    |  |

[Link to GitHub](https://github.com/Oceanwalker10/Prioritize)

###### tags: `Group Project` `Documentation`
