
import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { AuthService } from './auth.service';

describe('AuthService', () => {
  let service: AuthService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [AuthService]
    });
    service = TestBed.inject(AuthService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should log in successfully and store the token in localStorage', () => {
    const user = { username: 'testuser', password: 'testpassword' };
    const response = { accessToken: 'testToken' };

    service.login(user).then(result => {
      expect(result).toBe(true);
      expect(localStorage.getItem('authToken')).toBe('testToken');
    });

    const req = httpMock.expectOne('http://localhost:9098/app/signin');
    expect(req.request.method).toBe('POST');
    req.flush(response);
  });

  it('should handle login error and return false', () => {
    const user = { username: 'testuser', password: 'testpassword' };

    service.login(user).then(result => {
      expect(result).toBe(false);
      expect(localStorage.getItem('authToken')).toBeNull();
    });

    const req = httpMock.expectOne('http://localhost:9098/app/signin');
    expect(req.request.method).toBe('POST');
    req.flush(null, { status: 500, statusText: 'Internal Server Error' });
  });

  it('should retrieve the token from localStorage', () => {
    localStorage.setItem('authToken', 'testToken');

    const token = service.getToken();

    expect(token).toBe('testToken');
  });

  it('should remove the token from localStorage on logout', () => {
    localStorage.setItem('authToken', 'testToken');

    service.logout();

    expect(localStorage.getItem('authToken')).toBeNull();
  });
});

