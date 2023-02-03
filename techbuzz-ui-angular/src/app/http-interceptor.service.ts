import {Injectable} from '@angular/core';
import {HttpEvent, HttpHandler, HttpInterceptor, HttpRequest, HttpResponse} from "@angular/common/http";
import {map, Observable} from "rxjs";
import {AuthService} from "./auth.service";
import {Router} from "@angular/router";

@Injectable({
  providedIn: 'root'
})
export class HttpInterceptorService implements HttpInterceptor {

  constructor(private authService: AuthService,
              private router: Router) {
  }

  omitCalls = ['login', 'register'];
  skipInterceptor = false;

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    //console.log("---------------HttpInterceptorService-------------------")
    //console.log("req.url:", req.url)
    this.omitCalls.forEach(api => {
      if (req.url.includes(api)) {
        this.skipInterceptor = true;
      }
    });
    //console.log("skipInterceptor:", this.skipInterceptor)
    let token = this.authService.getAuthToken();
    if (token && !this.skipInterceptor) {
      const tokenizedReq = req.clone({
        headers: req.headers.set('Authorization', 'Bearer ' + token)
      });
      return next.handle(tokenizedReq).pipe(map((event: HttpEvent<any>) => {
        if (event instanceof HttpResponse) {
          if (event.status === 401) {
            this.authService.logout();
            this.router.navigateByUrl('/login');
          }
        }
        return event;
      }));
    }
    return next.handle(req);
  }
}
