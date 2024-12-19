describe('Login spec', () => {
  it('should login as admin successfull', () => {
    cy.loginAdmin();

    cy.get('button[routerlink="create"]').should('exist');
  });

  it('should login as user successfull', () => {
    cy.loginUser();

    cy.get('button[routerlink="create"]').should('not.exist');
  });

  it.only('should logout', () => {
    cy.loginUser();

    cy.get('span.link').contains('Logout').click();

    cy.url().should('eq', 'http://localhost:4200/');
  });

});