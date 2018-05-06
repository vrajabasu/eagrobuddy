import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { LoginService } from './loginService';
import { JwtHelper } from 'angular2-jwt';

declare const $: any;

@Component({
    selector: 'app-home-login',
    templateUrl: './login.component.html'
})

export class LoginComponent implements OnInit {
	loginForm: FormGroup;
	test: Date = new Date();
	errorMessage:String;
	statusCode:number;
	jwtHelper: JwtHelper = new JwtHelper();

	isExpired:boolean=true;
	

    constructor(private formBuilder: FormBuilder, private router: Router, private loginService: LoginService) { }

    ngOnInit() {
    	this.loginForm = this.formBuilder.group({
           userName: [null, [Validators.required, Validators.minLength(3)]],
           password: [null, Validators.required]
		  });

		  this.checkExpiry();
    }

    isFieldValid(form: FormGroup, field: string) {
	    return !form.get(field).valid && form.get(field).touched;
    }


	onLogin(userName, password) {
		// this.preProcessConfigurations();
		// if (this.loginForm.valid) {
		// 	this.loginService.login(userName, password)
			
		// 	.subscribe(() => {
     this.router.navigate(['../dashboard']);
   // }, errorCode =>  this.statusCode = errorCode);
			
	  // 	} else {
	  //   	this.validateAllFormFields(this.loginForm);
	  // 	}
	}

	validateAllFormFields(formGroup: FormGroup) {
	  	Object.keys(formGroup.controls).forEach(field => {
	    	const control = formGroup.get(field);
	    	if (control instanceof FormControl) {
	      		control.markAsTouched({ onlySelf: true });
	    	} else if (control instanceof FormGroup) {
	      		this.validateAllFormFields(control);
	    	}	
	  	});
	}	

	handleError(error) {
		switch (error.status) {
		  case 401:
		}
	  }
	  preProcessConfigurations() {
		this.statusCode = null;   
	 }

	  checkExpiry()
	  {
		let token= localStorage.getItem('jwt');

		if(token!=null&&this.jwtHelper.isTokenExpired(token))
		{
			this.isExpired=true;
		}
		else{
			this.isExpired=false;
		}
	  }

	}