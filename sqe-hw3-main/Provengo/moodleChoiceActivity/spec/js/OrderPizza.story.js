/* @provengo summon selenium */
const Old_Option='Yes'
const New_Option='No'


/**
 * This story opens a new browser window, goes to Moodle, Logs in the student,
 * goes to the Echot course then goes to a specific choice activity and changes
 * the student choice and updates it.
 */
story('student alters his choice in the choice activity', function () {
  let s = new SeleniumSession().start('http://localhost/')
  s.login({username: 'student', password: 'Student1999$', expectedWelcome:'Welcome back, Student!'})
  s.goToCourse({name: 'Echot'})
  s.goToChoice({href: 'http://localhost/mod/choice/view.php?id=75'})
  s.alterChoice()
})


/**
 * This story opens a new browser window, goes to Moodle, Logs in the teacher,
 * goes to the Echot course then goes to a specific choice activity and changes
 * its update option to New_Option(No).
 */
story('teacher changes the choice to not updated', function () {
  // the "with" statement makes it redundant to write "s." before each call to a defined event (like the story above)
  let s = new SeleniumSession().start('http://localhost/')
  s.login({username: 'rahafadmin', password: 'RahafAdmin12$',expectedWelcome:'Welcome back, Admin!'})
  s.goToCourse({name: 'Echot'})
  s.goToChoice({href: 'http://localhost/mod/choice/view.php?id=75'})
  s.gotoChiceOption({description: New_Option})
})



/**
 * This story opens a new browser window only before ChangeChoice has been done(function), goes to Moodle,
 * Logs in the teacher,goes to the Echot course then goes to a specific choice activity and checks if
 * the Choice Activty update option is Old_Option(Yes).
 */
story('check option before change', function () {
  // the "with" statement makes it redundant to write "s." before each call to a defined event (like the story above)
  interrupt(Any('ChangeChoice'), function (){
    let s = new SeleniumSession().start('http://localhost/')
    s.login({username: 'rahafadmin', password: 'RahafAdmin12$',expectedWelcome:'Welcome back, Admin!'})
    s.goToCourse({name: 'Echot'})
    s.goToChoice({href: 'http://localhost/mod/choice/view.php?id=75'})
    s.assertChoiceOption({description:Old_Option})
  })
})

/**
 * This story opens a new browser window only when ChangeChoice has been done(function), goes to Moodle,
 * Logs in the teacher,goes to the Echot course then goes to a specific choice activity and checks if
 * the Choice Activty update option is New_Option(No).
 */
story('check option after change', function () {
  on(Any('ChangeChoice'), function (){
    let s = new SeleniumSession().start('http://localhost/')
    s.login({username: 'rahafadmin', password: 'RahafAdmin12$',expectedWelcome:'Welcome back, Admin!'})
    s.goToCourse({name: 'Echot'})
    s.goToChoice({href: 'http://localhost/mod/choice/view.php?id=75'})
    s.assertChoiceOption({description:New_Option})
  })

})







