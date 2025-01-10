describe('Login spec', () => {
  it('should login as admin successfull', () => {
    cy.loginAdmin();

    cy.get('button[routerlink="create"]').should('exist');
  });

  it('should login as user successfull', () => {
    cy.loginUser();

    cy.get('button[routerlink="create"]').should('not.exist');
  });

  it('should logout', () => {
    cy.loginUser();

    cy.get('span.link').contains('Logout').click();

    cy.url().should('eq', 'http://localhost:4200/');
  });

  it('should display user admin datas', () => {
    const user = {
      id: 1,
      firstName: 'firstName',
      lastName: 'lastName',
      email: 'yoga@studio.com',
      admin: true
    };

    cy.intercept('GET', '/api/user/1', user);
    
    cy.loginAdmin();
    cy.get('span[routerlink="me"]').click();

    cy.get('[data-testid="emailParagraph"]').should('contain', user.email);
    cy.get('[data-testid="nameParagraph"]').should('contain', user.firstName);
    cy.get('[data-testid="nameParagraph"]').should('contain', user.lastName.toUpperCase());
  });

  it('should delete the logged in account', () => {
    cy.intercept('DELETE', '/api/user/1', {
      statusCode: 200,
    });
    
    const user = {
      id: 1,
      firstName: 'firstName',
      lastName: 'lastName',
      email: 'yoga@studio.com',
      admin: false
    };
    
    cy.intercept('GET', '/api/user/1', user);

    cy.loginUser();
    cy.get('span[routerlink="me"]').click();
    cy.get('button').contains('Detail').click();

    cy.url().should('eq', 'http://localhost:4200/')
  });
});