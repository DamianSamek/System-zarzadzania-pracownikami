package ur.edu.pl.project.exceptions;

public class ErrorResponseCodes {
	private ErrorResponseCodes() {
	}

	public static class Authentication {
		private Authentication() {
		}

		public static final String UNATHORIZED_ERROR = "unauthorized_error";
		public static final String AUTHENTICATION_ERROR = "authentication_error";
		public static final String TOKEN_EXPIRED = "token_expired";
		public static final String PASSWORDS_NOT_EQUAL = "passwords_not_equal";
	}

	public static class User {
		private User() {
		}

		public static final String USER_CREATE_ERROR = "user_create_error";
	}

	public static class InputOutput {
		private InputOutput() {
		}

		public static final String INPUT_OUTPUT_ERROR = "IO_error";
	}

	public static class Point {
		private Point() {
		}

		public static final String POINT_NOT_EXIST = "point_not_exist";
	}

	public static class Agreement {
		private Agreement() {
		}

		public static final String AGREEMENT_NOT_EXIST = "agreement_not_exist";
	}

	public static class Customer {
		private Customer() {
		}

		public static final String CUSTOMER_NOT_EXIST = "customer_not_exist";
	}

	public static class Issuer {
		private Issuer() {
		}

		public static final String ISSUER_NOT_EXIST = "issuer_not_exist";
	}

	public static class MaintenanceActionToPerform {
		private MaintenanceActionToPerform() {
		}

		public static final String ACTION_NOT_EXIST = "action_not_exist";
	}

	public static class MaintenanceServiceVisitReport {
		private MaintenanceServiceVisitReport() {
		}

		public static final String REPORT_NOT_EXIST = "report_not_exist";
	}

	public static class MaintenanceServiceVisit {
		private MaintenanceServiceVisit() {
		}

		public static final String VISIT_NOT_EXIST = "visit_not_exist";
	}

	public static class ServiceWorker {
		private ServiceWorker() {
		}

		public static final String SERVICE_WORKER_NOT_EXIST = "service_worker_not_exist";
	}

	public static class DailySchedule {
		private DailySchedule() {
		}

		public static final String DAILY_SCHEDULE_NOT_EXIST = "daily_schedule_not_exist";
	}
}