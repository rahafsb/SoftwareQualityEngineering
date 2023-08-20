/* @Provengo summon selenium */

/**
 * The StartSearch event defines the selenium action for clicking the search button.
 */
defineEvent(SeleniumSession, "Login", function(session, e) {
  with (session){
    sleep(1000)
    click("//a[contains(text(),'Log in')]");
    sleep(1000)
    writeText('//input[@id="username"]', e.username);
    writeText('//input[@id="password"]', e.password);
    sleep(3000)
    click('//button[@id="loginbtn"]');
   }
})



defineEvent(SeleniumSession, "GoToCourse", function(session, e) {
  with (session){
    bp.log.info(e);
    session.click("//a[contains(text(),'My courses')]");
   // timeout (10);
    if (e.href)
      waitForVisibility(session.click("//a[contains(text(),'Echot')"), 10)
      session.click("//*[@class='multiline' and contains(text(),'Echot')]")
  }

})



defineEvent(SeleniumSession, "AlterChoice", function(session, e) {
  with (session){
    sleep(2000);
    click("//input[@id='choice_1']", 0);
    sleep(2000);
   click("//input[@value='Save my choice']");
  }

})

defineEvent(SeleniumSession, "GotoChiceOption", function(session, e) {
  with (session){
    sleep(3000);
    session.click("//*[@role='menuitem' and contains(text(),'Settings')]")
    sleep(2000);
    session.click("//*[@id='id_allowupdate']")//1
    sleep(2000);
    session.click("//option[contains(text(),'No')]")
    sleep(2000);
    click("//input[@value='Save and return to course']");

  }

})
defineEvent(SeleniumSession, "AssertChoiceOption", function(session, e) {
  with (session){
    session.click("//*[@role='menuitem' and contains(text(),'Settings')]")
    sleep(1000);
    session.click("//*[@id='id_allowupdate']")//
    let noOption = session.find("//option[contains(text(),'No')]");
    let yesOption = session.find("//option[contains(text(),'Yes')]");
    if(e.description.EQUAL('No')){
      assert(noOption.length > 0, e.description);

    }
    else{
      assert(yesOption.length > 0, e.description);
    }
  }

})

defineEvent(SeleniumSession, "GoToChoice", function(session, e) {
  with (session){
    session.click("(//*[@class='activityname'])[1]");
    sleep(1000);
  }
})
