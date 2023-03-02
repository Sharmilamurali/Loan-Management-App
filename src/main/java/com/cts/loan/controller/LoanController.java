package com.cts.loan.controller;

import java.util.List;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cts.loan.entity.LoanEntity;
import com.cts.loan.entity.UserEntity;
import com.cts.loan.exception.LoanNotFoundException;
import com.cts.loan.service.LoanService;

@RestController
public class LoanController {

	@Autowired
	private LoanService loanService;

	@GetMapping("/viewAllLoan") // only Admin
	@CrossOrigin(origins = "http://localhost:4200")
	public ResponseEntity<?> viewAllLoan(@RequestHeader("Authorization") String token) throws LoanNotFoundException {
		try {
			return new ResponseEntity<>(loanService.getAllLoan(), HttpStatus.OK);

		} catch (LoanNotFoundException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);

		}
	}

	@GetMapping("/search") // both
	@CrossOrigin(origins = "http://localhost:4200")
	public ResponseEntity<?> search(@RequestHeader("Authorization") String token,
			@RequestParam(defaultValue = "") String loanNo, @RequestParam(defaultValue = "") String firstName,
			@RequestParam(defaultValue = "") String lastName) {
		try {
			return new ResponseEntity<>(loanService.searchByLoanNoAndOrFirstAndOrLastName(loanNo, firstName, lastName),
					HttpStatus.OK);
		} catch (LoanNotFoundException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}

	}

	@DeleteMapping("/deleteLoan/{loanNo}") // only Admin
	@CrossOrigin(origins = "http://localhost:4200")
	public ResponseEntity<?> deleteLoan(@RequestHeader("Authorization") String token,
			@PathVariable("loanNo") String loanNo) {

		try {

			return new ResponseEntity<>(loanService.deleteLoan(loanNo), HttpStatus.OK);

		} catch (LoanNotFoundException e) {

			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}

	}

	@PutMapping("/updateLoan/{loanNo}") // only Admin
	@CrossOrigin(origins = "http://localhost:4200")
	public ResponseEntity<?> updateLoan(@RequestHeader("Authorization") String token, @RequestBody LoanEntity loan,
			@PathVariable("loanNo") String loanNo) {

		try {

			return new ResponseEntity<>(loanService.updateLoan(loan, loanNo), HttpStatus.OK);

		} catch (LoanNotFoundException e) {

			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);

		}

	}

	@GetMapping("/viewUserLoan/{username}") // user's loan
	@CrossOrigin(origins = "http://localhost:4200")
	public ResponseEntity<?> viewUserLoan(@RequestHeader("Authorization") String token,
			@PathVariable("username") String username, @RequestParam(defaultValue = "") String loanNo) {
		try {
			UserEntity user = loanService.getUser(username);
			List<LoanEntity> loans = loanService.getLoanbyUser(user, loanNo);
			return new ResponseEntity<List<LoanEntity>>(loans, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}

	}

	@PostMapping("/createLoan") // only Admin
	@CrossOrigin(origins = "http://localhost:4200")
	public ResponseEntity<?> createLoan(@RequestHeader("Authorization") String token,
			@Valid @RequestBody LoanEntity loan) {
		try {

			return new ResponseEntity<>(loanService.addLoan(loan), HttpStatus.CREATED);
		} catch (Exception e) {

			return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
		}
	}

	@GetMapping("/viewLoanbyId/{loanNo}") // user's loan
	@CrossOrigin(origins = "http://localhost:4200")
	public ResponseEntity<?> viewLoanbyId(@RequestHeader("Authorization") String token,
			@PathVariable("loanNo") String loanNo) {
		try {
			LoanEntity loans = loanService.getLoanbyLoanNo(loanNo);
			return new ResponseEntity<LoanEntity>(loans, HttpStatus.OK);
		} catch (LoanNotFoundException e) {

			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}

	}

//	@GetMapping("/search") // both
//	@CrossOrigin(origins = "http://localhost:4200")
//	public ResponseEntity<?> search(@RequestHeader("Authorization") String token, @RequestParam String query) {
//		try {
//			return new ResponseEntity<>(loanService.searchLoan(query), HttpStatus.OK);
//		} catch (LoanNotFoundException e) {
//			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
//		}
//
//	}
//	@GetMapping("/viewRole/{username}") // user's loan
//	@CrossOrigin(origins = "http://localhost:4200")
//	public ResponseEntity<?> viewRole(@RequestHeader("Authorization") String token,
//			@PathVariable("username") String username) {
//		try {
//			UserEntity user = loanService.getUser(username);
//			RolesEntity rolesEntity = user.getRoles();
//			Integer roleId = rolesEntity.getRoleId();
//			String roleName = rolesEntity.getRoleName();
//			return new ResponseEntity<>(new RolesEntity(roleId, roleName, null), HttpStatus.OK);
//		} catch (Exception ex) {
//
//			return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
//		}
//
//	}
}
//
//@GetMapping("/viewUserLoan/{id}") // user's loan
//public ResponseEntity<List<LoanEntity>> viewUserLoan(@RequestHeader("Authorization") String token,
//		@PathVariable("id") String id) throws LoanNotFoundException {
//	UserEntity user = loanService.getUser(id).get();
//	List<LoanEntity> loans = loanService.getLoanbyUser(user);
//	return new ResponseEntity<List<LoanEntity>>(loans, HttpStatus.OK);
//
//}

//		 @DeleteMapping("/delete/{loanNo}")
//			public ResponseEntity<?> deleteLoan(@RequestHeader("Authorization") String token,
//					@PathVariable int loanNo)throws LoanNotFoundException {
//
//				ResponseEntity<?> entity;
//
//				Boolean flag = loanService.deleteLoan(loanNo);
//
//				if (flag) {
//
//					entity = new ResponseEntity<>("Deleted Successfully ", HttpStatus.OK);
//				} else {
//					entity = new ResponseEntity<>("Loan not found for this LoanNo", HttpStatus.NOT_FOUND);
//				}
//
//				return entity;
//			}
//		 @DeleteMapping("delete/{loanNo}")
//		 public void deleteLoan(){
//			 
//		 }

//		 @DeleteMapping("/delete/{loanNo}") ResponseEntity<?> deleteLoan(@RequestParam("query") String query)
//		                                                    throws RecordNotFoundException {
//		        loanService.deleteLoan(loanNo);
//		        return HttpStatus.FORBIDDEN;
//		    }
//		 
//		
//
//	}

//	@GetMapping("/viewUserLoan/{user}") // user's loan
//	public ResponseEntity<List<LoanEntity>> viewUserLoan(@RequestHeader("Authorization") String token,
//			@PathVariable("user") UserEntity user) throws LoanNotFoundException {
//		List<LoanEntity> loans = loanService.getLoanbyUser(user);
//		return new ResponseEntity<List<LoanEntity>>(loans, HttpStatus.OK);
//
//	}

/*
 * @PostMapping("/create") public ResponseEntity<EmployeeEntity>
 * create(@RequestBody LoanEntity loan) throws RecordNotFoundException {
 * 
 * LoanEntity loan = service.createLoan(loan); return new
 * ResponseEntity<LoanEntity>("Created Loan "+loan, HttpStatus.OK); }
 * 
 * @PostMapping("/createLoan") public ResponseEntity<?> createLoan(@RequestBody
 * LoanEntity loan) { try {
 * 
 * return new ResponseEntity<>(loanService.addLoan(loan), HttpStatus.CREATED); }
 * catch (Exception e) {
 * 
 * return new ResponseEntity<>(HttpStatus.CONFLICT); }
 * 
 * }
 * 
 */

/*
 * @PostMapping("/login") public ResponseEntity<?> login(@RequestBody LoanEntity
 * userlogincredentials) {
 * 
 * final UserDetails userdetails =
 * loanService.loadUserByUsername(userlogincredentials.getUsername()); String
 * generateToken = ""; if
 * (userdetails.getPassword().equals(userlogincredentials.getUserpassword())) {
 * generateToken = jwtutil.generateToken(userdetails); return new
 * ResponseEntity<>(generateToken, HttpStatus.OK); } else {
 * 
 * return new ResponseEntity<>("Not Accesible", HttpStatus.FORBIDDEN); } }
 */

/*
 * @GetMapping("/validate") public ResponseEntity<?>
 * getValidity(@RequestHeader("Authorization") String token) { AuthResponse res
 * = new AuthResponse(); try { if (token == null) { res.setValidstatus(false);
 * return new ResponseEntity<>(res, HttpStatus.FORBIDDEN); } else { String
 * token1 = token.substring(7); String username =
 * jwtutil.extractUsername(token1); UserDetails userdetails =
 * loanService.loadUserByUsername(username); if
 * (Boolean.TRUE.equals(jwtutil.validateToken(token1, userdetails))) {
 * 
 * res.setValidstatus(true);
 * 
 * } else { res.setValidstatus(false);
 * 
 * return new ResponseEntity<>(res, HttpStatus.FORBIDDEN);
 * 
 * } }
 * 
 * } catch (Exception e) { e.printStackTrace(); } return new
 * ResponseEntity<>(res, HttpStatus.OK); }
 * 
 * /*@GetMapping("/hi") public ResponseEntity<?>
 * homei(@RequestHeader("Authorization") String token) {
 * 
 * ResponseEntity<?> responseentity=getValidity(token);
 * 
 * if((boolean) responseentity.getBody()) { return new
 * ResponseEntity<>("welcome", HttpStatus.OK); } else { return new
 * ResponseEntity<>("not valid", HttpStatus.OK); }
 * 
 * }
 */
