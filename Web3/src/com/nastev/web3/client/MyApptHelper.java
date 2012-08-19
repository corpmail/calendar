package com.nastev.web3.client;

import com.bradrydzewski.gwt.calendar.client.Appointment;
import com.bradrydzewski.gwt.calendar.client.Calendar;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class MyApptHelper {

	private final GreetingServiceAsync greetingService = GWT
			.create(GreetingService.class);
	protected Calendar cal;

	public MyApptHelper() {

	}

	public MyApptHelper(Calendar cal) {
		this.cal = cal;
	}

	public void insertNewAppointment(Appointment app) {
		this.greetingService.saveAppointmen(app, new AsyncCallback<Integer>() {
			public void onFailure(Throwable caught) {
				Window.alert("RPC to sendEmail() failed.");
			}

			@Override
			public void onSuccess(Integer result) {
				System.out
						.println("greetingService.getAppointments2x" + result);
				greetingService.getAppointmentById(result,
						new AsyncCallback<Appointment>() {
							public void onFailure(Throwable caught) {
								Window.alert("RPC to getAppointments() failed.");
							}

							@Override
							public void onSuccess(Appointment result) {
								System.out
										.println("greetingService.getAppointments3x"
												+ result.getTitle());
								// ArrayList<Appointment> l1 = null;
								// cal.suspendLayout();
								cal.addAppointment(result);
								// cal.resumeLayout();
							}

						});
			}

		});
	}

	public void updateAppointment(Appointment appt) {
		this.greetingService.updateAppointmen(appt,
				new AsyncCallback<Integer>() {
					public void onFailure(Throwable caught) {
						Window.alert("RPC to sendEmail() failed.");

					}

					@Override
					public void onSuccess(Integer result) {
						System.out.println("greetingService.getAppointments2"
								+ result);
						greetingService.getAppointmentById(result,
								new AsyncCallback<Appointment>() {
									public void onFailure(Throwable caught) {
										Window.alert("RPC to getAppointments() failed.");
									}

									@Override
									public void onSuccess(Appointment result) {
										System.out
												.println("greetingService.getAppointments3"
														+ result.getTitle());

										// 3)Dann den Appt in den View einfügen
										cal.addAppointment(result);
									}

								});
					}

				});
	}

	public void deleteAppointment(Appointment appt) {
		final GreetingServiceAsync greetingService = GWT
				.create(GreetingService.class);
		greetingService.deleteAppointmen(appt, new AsyncCallback<Boolean>() {

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onSuccess(Boolean result) {
				
			}

		});
	}

}