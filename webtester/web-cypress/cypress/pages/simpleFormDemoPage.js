class SimpleFormDemoPage{

    visit() {
        cy.visit('/simple-form-demo')
    }
    
    getUserMessageInput() {
        return cy.get('#user-message')
    }

    getUserMessageSubmitButton() {
        return cy.get('#showInput')
    }

    getSum1Input() {
        return cy.get('#sum1')
    }

    getSum2Input() {
        return cy.get('#sum2')
    }

    getAdditionSubmitButton() {
        return cy.get('#gettotal > button')
    }

    getDisplayedUserMesage() {
        return cy.get('#message')
    }

    getAdditionResult() {
        return cy.get('#addmessage')
    }

    checkPagePresent(){
        this.getUserMessageSubmitButton().should('be.visible')
    }

    checkDefaultElementsVisible(){
        this.getSum1Input().should('be.visible')
        this.getSum2Input().should('be.visible')
        this.getUserMessageSubmitButton().should('be.visible')
        this.getAdditionSubmitButton().should('be.visible')
    }

 
 }
 
 export default SimpleFormDemoPage