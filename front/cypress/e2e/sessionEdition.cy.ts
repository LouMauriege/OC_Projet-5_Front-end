describe('Yoga Session Management E2E with Mocked Login', () => {
  const sessionData = {
    id: 1,
    name: 'Morning Yoga',
    date: '2024-12-25',
    teacher_id: 1,
    description: 'A relaxing yoga session to start the day.',
    users: [],
  };

  const adminToken = 'mocked-admin-token'; // Mocked token for admin access

  beforeEach(() => {
    // Mock the login process
    cy.intercept('POST', '/api/auth/login', {
      statusCode: 200,
      body: { token: adminToken },
    }).as('login');

    // Set the token in localStorage
    cy.visit('/login'); // Navigate to login page to set context
    cy.window().then((window) => {
      window.localStorage.setItem('authToken', adminToken);
    });

    // Mock API response for teacher list
    cy.intercept('GET', '/api/teachers', {
      statusCode: 200,
      body: [
        { id: 1, firstName: 'Teacher A', lastName: '' },
        { id: 2, firstName: 'Teacher B', lastName: '' },
      ],
    }).as('getTeachers');

    // Mock API response for session list
    cy.intercept('GET', '/api/session', {
      statusCode: 200,
      body: [sessionData],
    }).as('getSessions');
  });

  it('should create a new yoga session', () => {
    // Mock API response for session creation
    cy.intercept('POST', '/api/session', {
      statusCode: 201,
      body: { ...sessionData, id: 2 },
    }).as('createSession');

    // Navigate to the Create Session page
    cy.visit('/sessions/create');

    // Fill out the form
    cy.get('input[formControlName="name"]').type('Morning Yoga');
    cy.get('input[formControlName="date"]').type('2024-12-25');
    cy.get('mat-select[formControlName="teacher_id"]').click();
    cy.contains('mat-option', 'Teacher A').click();
    cy.get('textarea[formControlName="description"]').type(
      'A relaxing yoga session to start the day.'
    );

    // Submit the form
    cy.get('button[type="submit"]').click();

    // Verify that the mocked API call was made
    cy.wait('@createSession').its('request.body').should('include', {
      name: 'Morning Yoga',
      date: '2024-12-25',
      teacher_id: 1,
      description: 'A relaxing yoga session to start the day.',
    });

    // Verify success message
    cy.contains('Session created!').should('be.visible');
  });

  // Other tests (update and delete) remain the same...
});
