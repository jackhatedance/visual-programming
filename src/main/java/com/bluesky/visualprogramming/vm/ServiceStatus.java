package com.bluesky.visualprogramming.vm;


public enum ServiceStatus {
	Initialized{

		@Override
		public void assertTransit(ServiceStatus newStatus) {
			if (newStatus != Running)
				throwException(this, newStatus);
		}
	}, Running {
		 

		@Override
		public void assertTransit(ServiceStatus newStatus) {
			if (newStatus != Paused && newStatus != Stopped)
				throwException(this, newStatus);
		}
	},
	Paused {
		@Override
		public void assertTransit(ServiceStatus newStatus) {
			if (newStatus != Running)
				throwException(this, newStatus);
		}
	},
	Stopped {
		@Override
		public void assertTransit(ServiceStatus newStatus) {
			if (newStatus != Running && newStatus != Destroyed)
				throwException(this, newStatus);
		}
	},
	Destroyed {
		@Override
		public void assertTransit(ServiceStatus newStatus) {
			// it is the final status
			throwException(this, newStatus);
		}
	};

	 
/**
 * assert function. throw exception if not allowed.
 * @param newStatus
 */
	public abstract void assertTransit(ServiceStatus newStatus);

	protected void throwException(ServiceStatus from, ServiceStatus to) {
		throw new RuntimeException(String.format(
				"service cannot transit from %s to %s", from.name(), to.name()));
	}
}
