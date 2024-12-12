import { HttpClientModule } from '@angular/common/http';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { MatToolbarModule } from '@angular/material/toolbar';
import { RouterTestingModule } from '@angular/router/testing';
import { expect } from '@jest/globals';

import { AppComponent } from './app.component';
import { Router } from '@angular/router';
import { SessionService } from './services/session.service';
import { of } from 'rxjs';


describe('AppComponent', () => {
  let app: AppComponent;
  let fixture: ComponentFixture<AppComponent>;
  let mockRouter: any;
  let mockSessionService: any;

  beforeEach(async () => {
    mockRouter = {
      navigate: jest.fn()
    }

    mockSessionService = {
      logOut: jest.fn(),
      $isLogged: jest.fn().mockReturnValue(of(true))
    }

    await TestBed.configureTestingModule({
      declarations: [
        AppComponent
      ],
      providers: [
        { provide: Router, useValue: mockRouter },
        { provide: SessionService, useValue: mockSessionService }
      ],
      imports: [
        RouterTestingModule,
        HttpClientModule,
        MatToolbarModule
      ],
    }).compileComponents();

    fixture = TestBed.createComponent(AppComponent);
    app = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create the app', () => {
    expect(app).toBeTruthy();
  });

  // it('should log out and navigate to home', () => {
  //   app.logout();

  //   expect(mockSessionService.logOut).toHaveBeenCalled();
  //   expect(mockRouter.navigate).toHaveBeenCalledWith(['']);
  // });
});
