import { HttpClientModule } from '@angular/common/http';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ReactiveFormsModule } from '@angular/forms';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { RouterTestingModule } from '@angular/router/testing';
import { expect } from '@jest/globals';
import { of, throwError } from 'rxjs';
import { SessionService } from 'src/app/services/session.service';
import { LoginComponent } from './login.component';
import { AuthService } from '../../services/auth.service';
import { Router } from '@angular/router';
import { By } from '@angular/platform-browser';

describe('LoginComponent', () => {
  let component: LoginComponent;
  let fixture: ComponentFixture<LoginComponent>;

  let mockAuthService: any;
  let mockSessionService: any;
  let mockRouter: any;

  beforeEach(async () => {
    mockAuthService = {
      login: jest.fn()
    };

    mockSessionService = {
      logIn: jest.fn()
    };
    
    mockRouter = {
      navigate: jest.fn()
    };

    await TestBed.configureTestingModule({
      declarations: [LoginComponent],
      providers: [
        { provide: AuthService, useValue: mockAuthService },
        { provide: SessionService, useValue: mockSessionService },
        { provide: Router, useValue: mockRouter },
      ],
      imports: [
        RouterTestingModule,
        BrowserAnimationsModule,
        HttpClientModule,
        MatCardModule,
        MatIconModule,
        MatFormFieldModule,
        MatInputModule,
        ReactiveFormsModule]
    }).compileComponents();

    fixture = TestBed.createComponent(LoginComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should call AuthService.login and navigate to /sessions on successful login', () => {
    const mockResponse = { sessionId: '123' };
    mockAuthService.login.mockReturnValue(of(mockResponse));

    component.form.setValue({ email: 'test@example.com', password: 'password' });
    component.submit();

    expect(mockAuthService.login).toHaveBeenCalledWith({
      email: 'test@example.com',
      password: 'password',
    });
    expect(mockSessionService.logIn).toHaveBeenCalledWith(mockResponse);
    expect(mockRouter.navigate).toHaveBeenCalledWith(['/sessions']);
  });

  it('should mark email as invalid if empty', () => {
    const emailControl = component.form.get('email');
    emailControl?.setValue('');
    expect(emailControl?.valid).toBeFalsy();
    expect(emailControl?.errors).toEqual({ required: true });
  });

  it('should mark password as invalid if empty', () => {
    const emailControl = component.form.get('password');
    emailControl?.setValue('');
    expect(emailControl?.valid).toBeFalsy();
    expect(emailControl?.errors).toEqual({ required: true });
  });

  it('should set onError on login failure', () => {
    mockAuthService.login.mockReturnValue(throwError(() => new Error('Login failed')));

    component.form.setValue({ email: 'test@example.com', password: 'password' });
    component.submit();
    fixture.detectChanges();

    expect(component.onError).toBeTruthy();

    const errorElement = fixture.debugElement.query(By.css('.error'));
    expect(errorElement).toBeTruthy();
    expect(errorElement.nativeElement.textContent).toContain('An error occurred');
  });

  it('should not display error message on successful login', () => {
    const mockResponse = { sessionId: '123' };
    mockAuthService.login.mockReturnValue(of(mockResponse));

    component.form.setValue({ email: 'test@example.com', password: 'password' });
    component.submit();
    fixture.detectChanges();

    expect(component.onError).toBeFalsy();

    const errorElement = fixture.debugElement.query(By.css('.error'));
    expect(errorElement).toBeFalsy();
  });

  it('should disable the submit button if a form field is invalid', () => {
    const submitButton = fixture.debugElement.query(By.css('button[type="submit"]'));
    expect(submitButton.nativeElement.disabled).toBeTruthy();

    component.form.get('email')?.setValue('test@example.com');
    fixture.detectChanges();
    expect(submitButton.nativeElement.disabled).toBeTruthy();

    component.form.get('password')?.setValue('password');
    fixture.detectChanges();
    expect(submitButton.nativeElement.disabled).toBeFalsy();
  });
});