import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss']
})
export class DashboardComponent implements OnInit {
  routerC: Router;

  constructor(router: Router, private route: ActivatedRoute) {
    this.routerC = router;
  }

  ngOnInit(): void {
  }

}
