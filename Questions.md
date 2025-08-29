1. Difference b/w Optional<> and ResponseEntity<>?

Optional<> -> presence/absence of a value; used in service/repo layers

ResponseEntity<> -> to give a full HTTP response with status, headers and body; used in controller layer


2. Difference b/w @PathVariable and @RequestParam?

@PathVariable -> to get a specific thing like a user with Id 5
it can handle multiple fields, but it's rigid and messy -> we need to pass the values in the url exactly in the specified order
filtering and sorting isn't available
all the fields mentioned are required

@RequestParam -> can only pass what we need
no specific order to pass values
filtering and sorting available

3. @NoArgsConstructor and @AllArgsConstructor? - provided by Lombok to write constructors w/o creating from scratch

@NoArgsConstructor -> to generate a no arguments constructor; needed for jpa/hibernate
entities should always have a @NoArgsConstructor as required by JPA
using frameworks or deserializing JSON

@AllArgsConstructor -> to generate a constructor with all fields as arguments; needed for manual object creation with all fields
can be used when we are passing entire dto via API

4. Exception Handling in Spring

i) create specific exceptions to provide meaningful information about the error
ii) @ControllerAdvice/@RestControllerAdvice, @ExceptionHandler - centralized/global exception handler -> catch exceptions globally & return custom error responses w/o cluttering business logic
@RestControllerAdvice makes it available across all our controllers
using composition over inheritance -> as java doesn't support multiple inheritance
iii) checked exceptions -> for situations where the caller can recover or take action like validation failures, missing data
unchecked exceptions -> irrecoverable situations, null pointer exceptions or database connection failures
iv) to provide meaningful and custom error messages to avoid showing sensitive info like stack traces in the prod 
v) @ResponseStatus -> automatically associates HTTP status codes with exceptions
@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Resource not found")

=> Local Exception Handling 
* used in controller level
* local to that specific controller
* best when only one controller needs it

5. Entity Mapping

to map the entities with their corresponding database tables
fields <-> columns
relationships <-> reflect foreign keys, join tables

@Entity - mark a class as jpa entity; maps to a table in DB
@Table - give table name to entity
@Id - marks it as primary key
@Column - maps to DB column
* name
* length
* nullable

Fetching types: Load the associated data of the other entity,

i) Lazy -> only when fetch when requested; on demand
ii) Eager -> load the data before hand; bit costly

Relationships

OneToMany: LAZY
ManyToOne: EAGER
ManyToMany: LAZY
OneToOne: EAGER

Cascade Types -> tell JPA what to do with related entities when we perform persist(), save(), delete() ... operations

i) CascadeType.PERSIST -> Saves child entity when parent is saved
ii) MERGE -> Updates child when parent is updated
iii) REFRESH -> Deletes child when parent is deleted
iv) REMOVE -> Refreshes child when parent is refreshed
v) DETACH -> Detaches child when parent is detached
vi) ALL -> Propagates all operations: persist, merge, remove, refresh, detach; 
only use ALL when child entity is completely dependent on parent


@Transactional -> helps in atomic operations; either a DB operation results in all success or all fail
* if transaction is successful, the changes will be committed; if not rolled back
* can be used at class level or method level
* if the transaction already exists, the marked method/class will execute within that transaction
* otherwise will create a new one
* propagation attribute -> a txn should always be created irrespective of the existence of txn
* isolation -> how much a txn should be isolated from others
* timeout -> max time a txn should be completed in
* @EnableTransactionManagement -> to enable txn mgmt

@Async -> used for performance optimization
* lets us run code in the background, main thread can keep going w/o waiting for slower tasks to finish
* @EnableAsync on the class
* makes method execution asynchronous
* we can't call an @Async method from the same class
* when @Async is called, it is executed in a different thread & the calling thread moves onto next task
* @Async & @Transactional doesn't go together

@EnableScheduling -> enables scheduling to a configuration class/main application class
@Scheduled(cron = "0 15 * * *") -> schedule tasks to run at fixed intervals, fixed rates or using cron expressions
helps to run periodic jobs
3 ways to configure scheduling
* fixed delay -> executes the method with a fixed period; after the method is run, it will wait for the specified period before running it next time
* fixed rate -> executes the method after the specified period regardless of the previous execution finish
* cron expr -> custom, complex scheduling at specified time intervals
* initial delay -> delay before the first execution; used when waiting for DB connections to stabilize/ cloud services to load configurations
* zone -> timezone specific scheduling
* fixedRateString -> if we want to change the task frequency w/o recompiling the entire code, 
change the property in application.properties and pass it @Scheduled
used for testing, adjust intervals based on load

@OneToMany, @ManyToOne, @OneToOne, @ManyToMany
PagingAndSortingRepository,
native queries, dynamic queries

Validation
* @Valid -> triggers validation for a parameter before the method is invoked
* @Validated -> apply validation to a specific groups of fields

Bean Validation API: 
* @NotNull -> makes sure that a field is not null 
* @NotBlank -> atleast 1 non whitespace character
* @NotEmpty -> collections/arrays are not empty
* @Min, @Max -> related to numeric values tries to validate it based on the given value
* @Size(min, max) -> checks if a string is within specified range 
* @Pattern(regex) -> checks if a field matches the provided regex
* @Email -> valid address format
* @AssertTrue, @AssertFalse -> a boolean field is true/false resp

* getBindingResult() -> extracts the BindingResult - holds the result of validation
* getFieldErrors() -> returns a list of field errors - each one representing a failed validation on a specific field
* getField() -> returns the name of the field that caused validation error
* getDefaultMessage() -> returns the message associated with that particular field in DTO

Custom validation annotations -> create a new annotation to specify validation rules to fields/methods
@Target -> where annotation can be applied
@Retention -> how long the annotation should be retained
@Constraint -> specify the validator class for implementing validation logic

CustomValidator -> class that implements the ConstraintValidator interface
initialize() -> initialize the validator, access any annotation attributes if needed
isValid() -> actual validation logic

   
5. Spring Boot Security
   •	Basic authentication and role-based access
   •	JWT-based security
   •	OAuth2 (if needed)
   •	Method-level security (@PreAuthorize, @Secured)
   •	Password encoding (BCrypt)

CSRF -> cross site request forgery



