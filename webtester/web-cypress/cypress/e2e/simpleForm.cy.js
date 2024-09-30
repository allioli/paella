import CookiesDialog from "../pages/cookiesDialog"
import SimpleFormDemoPage from "../pages/simpleFormDemoPage";

describe('User interacts with Simple Form', () => {
  const sumFixtures = require('../fixtures/sums')
  const cookiesDialog = new CookiesDialog();
  const simpleFormDemoPage = new SimpleFormDemoPage();

  beforeEach(() => {
    // GIVEN I Open simple form demo page
    simpleFormDemoPage.visit();

    // WHEN I Dismiss cookies dialog
    cookiesDialog.getAcceptAllButton()
      .click()
    
    // THEN I see simple form demo page
    simpleFormDemoPage.checkPagePresent()

    cy.url().should('include', '/simple-form-demo')
  })

  it('Enter text using a form', () => {
     // WHEN I verify "Simple Form Demo" page
     simpleFormDemoPage.checkDefaultElementsVisible()

     // AND I enter "bla bla" to the message form
     simpleFormDemoPage.getUserMessageInput()
      .clear()
      .type('bla bla')

     // AND I click on Get checked value button
     simpleFormDemoPage.getUserMessageSubmitButton().click()

     // THEN I should see the entered message as "bla bla"
     simpleFormDemoPage.getDisplayedUserMesage()
      .should('have.text', 'bla bla')
  })

  sumFixtures.forEach((sum_fixture) => {
    it(`Form adding ${sum_fixture.number1} and ${sum_fixture.number2}`, () => {
      // WHEN I enter <number1> and <number2> to the sum up form
      simpleFormDemoPage.getSum1Input().type(sum_fixture.number1)
      simpleFormDemoPage.getSum2Input().type(sum_fixture.number2)

      // AND I click on Sum button
      simpleFormDemoPage.getAdditionSubmitButton().click()

      // THEN I should see the result of the sum as <result>
      simpleFormDemoPage.getAdditionResult()
        .should('have.text', sum_fixture.result)

    })
  })
})