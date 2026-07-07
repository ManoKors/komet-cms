export type ToastType = 'success' | 'error';

class ToastStore {
	visible = $state(false);
	message = $state('');
	type = $state<ToastType>('success');
	private timeoutId: ReturnType<typeof setTimeout> | null = null;

	show(msg: string, toastType: ToastType = 'success', durationMs: number = 3000) {
		this.message = msg;
		this.type = toastType;
		this.visible = true;

		if (this.timeoutId) {
			clearTimeout(this.timeoutId);
		}

		this.timeoutId = setTimeout(() => {
			this.hide();
		}, durationMs);
	}

	hide() {
		this.visible = false;
		if (this.timeoutId) {
			clearTimeout(this.timeoutId);
			this.timeoutId = null;
		}
	}
}

export const toast = new ToastStore();
