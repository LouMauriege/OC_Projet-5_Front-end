describe('Session inscription for users', () => {
    // beforeEach(() => {
    //     cy.intercept('GET', '/api/sessionl', );
    // });

    it('should participate to session', () => {
        cy.loginUser();
    });

    it('should not be able to create or edit session', () => {
        cy.loginUser();
    });
});