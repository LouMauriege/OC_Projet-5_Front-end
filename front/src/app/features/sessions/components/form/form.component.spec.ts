import { HttpClientModule } from '@angular/common/http';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ReactiveFormsModule } from '@angular/forms';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { MatSnackBar, MatSnackBarModule } from '@angular/material/snack-bar';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { RouterTestingModule } from '@angular/router/testing';
import { expect } from '@jest/globals';
import { SessionService } from 'src/app/services/session.service';
import { SessionApiService } from '../../services/session-api.service';

import { FormComponent } from './form.component';
import { of } from 'rxjs';
import { TeacherService } from 'src/app/services/teacher.service';
import { ActivatedRoute, Router } from '@angular/router';
import { By } from '@angular/platform-browser';

describe('FormComponent', () => {
  let component: FormComponent;
  let fixture: ComponentFixture<FormComponent>;

  let mockSessionService: any;
  let mockSessionApiService: any;
  let mockTeacherService: any;
  let mockSnackBar: any;
  let mockRouter: any;
  let mockActivatedRoute: any;

  beforeEach(async () => {

    mockSessionService = {
      sessionInformation: {
        admin: true
      }
    }

    mockSessionApiService = {
      create: jest.fn().mockReturnValue(of({})),
      update: jest.fn().mockReturnValue(of({})),
      detail: jest.fn().mockReturnValue(of({
        id: '1',
        name: 'Test Session',
        users: [1, 2, 3],
        teacher_id: 101,
        date: new Date(),
        description: 'Session description',
        createdAt: new Date(),
        updatedAt: new Date()
      })),
    };

    mockTeacherService = {
      all: jest.fn().mockReturnValue(of([
        { id: '101', name: 'Teacher 1' },
        { id: '102', name: 'Teacher 2' }
      ]))
    };

    mockSnackBar = {
      open: jest.fn()
    };

    mockActivatedRoute = {
      snapshot: {
        paramMap: {
          get: jest.fn().mockReturnValue('1')
        }
      }
    }

    mockRouter = {
      navigate: jest.fn(),
      url: 'sessions/update/1',
      navigateByUrl: jest.fn()
    }

    await TestBed.configureTestingModule({
      imports: [
        RouterTestingModule,
        HttpClientModule,
        MatCardModule,
        MatIconModule,
        MatFormFieldModule,
        MatInputModule,
        ReactiveFormsModule, 
        MatSnackBarModule,
        MatSelectModule,
        BrowserAnimationsModule
      ],
      providers: [
        { provide: SessionService, useValue: mockSessionService },
        { provide: SessionApiService, useValue: mockSessionApiService },
        { provide: TeacherService, useValue: mockTeacherService },
        { provide: MatSnackBar, useValue: mockSnackBar },
        { provide: Router, useValue: mockRouter },
        { provide: ActivatedRoute, useValue: mockActivatedRoute }
      ],
      declarations: [FormComponent]
    }).compileComponents();

    fixture = TestBed.createComponent(FormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should initialize form in update mode', () => {
    expect(component.onUpdate).toBe(true);
    expect(mockSessionApiService.detail).toHaveBeenCalledWith('1');
      expect(component.sessionForm?.value).toEqual({
        name: 'Test Session',
        description: 'Session description',
        date: new Date().toISOString().split('T')[0],
        teacher_id: 101
      });
  });

  it('should submit the form and create a session', () => {
    component.onUpdate = false; // Simulate create mode
    component.sessionForm?.setValue({
      name: 'New Session',
      date: new Date().toISOString().split('T')[0],
      teacher_id: '102',
      description: 'New Description'
    });

    component.submit();

    expect(mockSessionApiService.create).toHaveBeenCalledWith({
      name: 'New Session',
      date: new Date().toISOString().split('T')[0],
      teacher_id: '102',
      description: 'New Description'
    });
    expect(mockSnackBar.open).toHaveBeenCalledWith('Session created !', 'Close', { duration: 3000 });
    expect(mockRouter.navigate).toHaveBeenCalledWith(['sessions']);
  });

  it('should submit the form and update a session', () => {
    component.onUpdate = true;
    component.sessionForm?.setValue({
      name: 'Updated Session',
      date: new Date().toISOString().split('T')[0],
      teacher_id: '101',
      description: 'Updated Description'
    });

    component.submit();

    expect(mockSessionApiService.update).toHaveBeenCalledWith('1', {
      name: 'Updated Session',
      date: new Date().toISOString().split('T')[0],
      teacher_id: '101',
      description: 'Updated Description'
    });
    expect(mockSnackBar.open).toHaveBeenCalledWith('Session updated !', 'Close', { duration: 3000 });
    expect(mockRouter.navigate).toHaveBeenCalledWith(['sessions']);
  });

  it('should disable the submit button when the form is invalid', () => {
    const submitButton = fixture.debugElement.query(By.css('button[type="submit"]'));

    component.sessionForm?.get('name')?.setValue('');
    fixture.detectChanges();
    expect(submitButton.nativeElement.disabled).toBeTruthy();

    component.sessionForm?.get('name')?.setValue('toto');
    component.sessionForm?.get('date')?.setValue('')
    fixture.detectChanges();
    expect(submitButton.nativeElement.disabled).toBeTruthy();

    component.sessionForm?.get('date')?.setValue('2024-06-17');
    component.sessionForm?.get('teacher_id')?.setValue('')
    fixture.detectChanges();
    expect(submitButton.nativeElement.disabled).toBeTruthy();


    component.sessionForm?.get('teacher_id')?.setValue(1);
    component.sessionForm?.get('description')?.setValue('')
    fixture.detectChanges();
    expect(submitButton.nativeElement.disabled).toBeTruthy();

    component.sessionForm?.get('description')?.setValue('Yoga class')
    fixture.detectChanges();
    expect(submitButton.nativeElement.disabled).toBeFalsy();
  });
});
