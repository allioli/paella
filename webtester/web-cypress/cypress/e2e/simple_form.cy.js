describe('User interacts with Simple Form', () => {
  const sum_fixtures = require('../fixtures/sums')

  beforeEach(() => {
    // GIVEN I Open simple form demo page
    cy.visit('/simple-form-demo')

    // WHEN I Dismiss cookies dialog
    cy.get('#CybotCookiebotDialogBodyLevelButtonLevelOptinAllowallSelection')
      .click()
    
    // THEN I see simple form demo page
    cy.get('#user-message').should('be.visible')
    cy.url().should('include', '/simple-form-demo')
  })

  it('Enter text using a form', () => {
     // WHEN I verify "Simple Form Demo" page
     cy.get('#sum1').should('be.visible')
     cy.get('#sum2').should('be.visible')
     cy.get('#showInput').should('be.visible')
     cy.get('#gettotal > button').should('be.visible')

     // AND I enter "bla bla" to the message form
     cy.get('#user-message')
      .clear()
      .type('bla bla')

     // AND I click on Get checked value button
     cy.get('#showInput').click()

     // THEN I should see the entered message as "bla bla"
     cy.get('#message')
      .should('have.text', 'bla bla')
  })

  sum_fixtures.forEach((sum_fixture) => {
    it(`Form adding ${sum_fixture.number1} and ${sum_fixture.number2}`, () => {
      // WHEN I enter <number1> and <number2> to the sum up form
      cy.get('#sum1').type(sum_fixture.number1)
      cy.get('#sum2').type(sum_fixture.number2)

      // AND I click on Sum button
      cy.get('#gettotal > button').click()

      // THEN I should see the result of the sum as <result>
      cy.get('#addmessage')
      .should('have.text', sum_fixture.result)

    })
  })
})