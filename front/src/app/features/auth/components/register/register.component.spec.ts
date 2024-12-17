import { HttpClientModule } from '@angular/common/http';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { FormBuilder, ReactiveFormsModule } from '@angular/forms';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { expect } from '@jest/globals';

import { RegisterComponent } from './register.component';
import { AuthService } from '../../services/auth.service';
import { Router } from '@angular/router';
import { of, throwError } from 'rxjs';
import { By } from '@angular/platform-browser';

describe('RegisterComponent', () => {
  let component: RegisterComponent;
  let fixture: ComponentFixture<RegisterComponent>;

  let mockAuthService: any;
  let mockRouter: any;

  beforeEach(async () => {
    mockAuthService = {
      register: jest.fn()
    }

    mockRouter = {
      navigate: jest.fn()
    }

    await TestBed.configureTestingModule({
      declarations: [RegisterComponent],
      providers: [
         { provide: AuthService, useValue: mockAuthService },
         { provide: Router, useValue: mockRouter }
      ],
      imports: [
        BrowserAnimationsModule,
        HttpClientModule,
        ReactiveFormsModule,  
        MatCardModule,
        MatFormFieldModule,
        MatIconModule,
        MatInputModule
      ]
    }).compileComponents();

    fixture = TestBed.createComponent(RegisterComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should call AuthService.register and navigate to /login on successful submit', () => {
    const mockFormValues = {
      email: "test@mail.com",
      firstName: "toto",
      lastName: "tata",
      password: 'passwd'
    }

    mockAuthService.register.mockReturnValue(of(void 0));

    component.form.setValue(mockFormValues);

    component.submit();

    expect(mockAuthService.register).toHaveBeenCalledWith(mockFormValues);
    expect(mockRouter.navigate).toHaveBeenCalledWith(['/login']);
    expect(component.onError).toBe(false);
  });

  it('should set onError to true on failed submit', () => {
    const mockRegisterRequest = {
      email: 'test@example.com',
      firstName: 'John',
      lastName: 'Doe',
      password: 'password123',
    };
    mockAuthService.register.mockReturnValue(throwError(() => new Error('Registration failed')));

    component.form.setValue(mockRegisterRequest);

    component.submit();

    expect(mockAuthService.register).toHaveBeenCalledWith(mockRegisterRequest);
    expect(component.onError).toBe(true);
    expect(mockRouter.navigate).not.toHaveBeenCalled();
  });

  it('should mark email as invalid if empty', () => {
    const emailControl = component.form.get('email');
    emailControl?.setValue('');
    expect(emailControl?.valid).toBeFalsy();
    expect(emailControl?.errors).toEqual({ required: true });
  });

  it('should mark firstname as invalid if empty', () => {
    const emailControl = component.form.get('firstName');
    emailControl?.setValue('');
    expect(emailControl?.valid).toBeFalsy();
    expect(emailControl?.errors).toEqual({ required: true });
  });

  it('should mark lastname as invalid if empty', () => {
    const emailControl = component.form.get('lastName');
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

  it('should set on error on register failure and display message error', () => {
    const mockRegisterRequest = {
      email: 'test@example.com',
      firstName: 'John',
      lastName: 'Doe',
      password: 'password123',
    };
    mockAuthService.register.mockReturnValue(throwError(() => (new Error('Register failed'))));

    component.form.setValue(mockRegisterRequest);
    component.submit();

    expect(component.onError).toBeTruthy();

    fixture.detectChanges();
    const errorElement = fixture.debugElement.query(By.css('.error'));
    expect(errorElement).toBeTruthy();
    expect(errorElement.nativeElement.textContent).toContain('An error occurred');
  })

  it('should not display error message on successful register', () => {
    const mockResponse = { sessionId: '123' };
    mockAuthService.register.mockReturnValue(of(void 0));

    component.form.setValue({ email: 'test@example.com', lastName:'tata', firstName:'toto', password: 'password'});
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

    component.form.get('firstName')?.setValue('firstName');
    fixture.detectChanges();
    expect(submitButton.nativeElement.disabled).toBeTruthy();

    component.form.get('lastName')?.setValue('lastName');
    fixture.detectChanges();
    expect(submitButton.nativeElement.disabled).toBeTruthy();

    component.form.get('password')?.setValue('password');
    fixture.detectChanges();
    expect(submitButton.nativeElement.disabled).toBeFalsy();
  });
});
