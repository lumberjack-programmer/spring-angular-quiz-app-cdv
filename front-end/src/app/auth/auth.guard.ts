import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree } from '@angular/router';
import { Observable, map } from 'rxjs';
import { AuthService } from './auth.service';

@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {

  constructor(private authService: AuthService, private router: Router) {}

  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot):  Observable<boolean> | Promise<boolean> | boolean {
    return this.authService.isAuthenticated().pipe(
      map((result) => {
        if (result) {
          console.log(`canActivate : true`);
          console.log(`Role: ${route.data['role']}`);
          console.log(typeof route.data['role']);
          if (route.data['role'] && route.data['role'] === 'ROLE_ADMIN') {
            return true;
          } else if (route.data['role'] === undefined) {
            return true;
          } else {
            this.router.navigate(['/login']);
            return false;
          }
        } else {
          console.log(`canActivate : this.router.navigate(['/login'])`);
          this.router.navigate(['/login']);
          return false;
        }
      })
    );
  }
}
