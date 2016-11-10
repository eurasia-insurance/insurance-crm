function hideIfValidated(dialog, args) {
	if (!args.validationFailed) {
		dialog.hide();
	}
}
