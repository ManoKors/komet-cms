<script lang="ts">
	import TextInput from '$lib/components/TextInput.svelte';
	import ListRepeater from '$lib/components/ListRepeater.svelte';
	import SelectInput from '$lib/components/SelectInput.svelte';
	import type { ListItem } from '$lib/components/ListRepeater.svelte';
	import { toast } from '$lib/stores/toast.svelte';

	let { data } = $props();

	let title = $state(data.title);
	let subtitle = $state(data.subtitle);
	let services = $state<ListItem[]>(data.services);
	let theme = $state(data.theme);

	let buttonText = $state('Speichern');
	let isSaving = $state(false);

	const themeOptions = [
		{ value: 'minimal-light', label: 'Minimal Hell' },
		{ value: 'classic-dark', label: 'Klassisch Dunkel' }
	];

	async function handleSubmit(event: Event) {
		event.preventDefault();
		isSaving = true;
		buttonText = 'Speichert...';

		try {
			const [heroResponse, servicesResponse, settingsResponse] = await Promise.all([
				fetch('http://localhost:8080/api/v1/content/tenant-123/hero', {
					method: 'POST',
					headers: {
						'Content-Type': 'application/json'
					},
					body: JSON.stringify({
						title,
						subtitle
					})
				}),
				fetch('http://localhost:8080/api/v1/content/tenant-123/services', {
					method: 'POST',
					headers: {
						'Content-Type': 'application/json'
					},
					body: JSON.stringify({
						items: services
					})
				}),
				fetch('http://localhost:8080/api/v1/content/tenant-123/settings', {
					method: 'POST',
					headers: {
						'Content-Type': 'application/json'
					},
					body: JSON.stringify({
						theme
					})
				})
			]);

			if (heroResponse.ok && servicesResponse.ok && settingsResponse.ok) {
				buttonText = 'Gespeichert!';
				toast.show('Änderungen erfolgreich gespeichert.', 'success');
				setTimeout(() => {
					buttonText = 'Speichern';
				}, 2000);
			} else {
				buttonText = 'Fehler beim Speichern (teilweise)';
				toast.show('Einige Änderungen konnten nicht gespeichert werden.', 'error');
				setTimeout(() => {
					buttonText = 'Speichern';
				}, 3000);
			}
		} catch (error) {
			console.error('Fetch error:', error);
			buttonText = 'Fehler beim Speichern';
			toast.show('Ein unerwarteter Fehler ist aufgetreten.', 'error');
			setTimeout(() => {
				buttonText = 'Speichern';
			}, 3000);
		} finally {
			isSaving = false;
		}
	}
</script>

<svelte:head>
	<title>Komet CMS - Dashboard</title>
</svelte:head>

<div class="max-w-ghost-content mx-auto mt-10 p-ghost-side">
	<h1 class="text-3xl font-bold text-ghost-black mb-6">Dashboard</h1>

	<form onsubmit={handleSubmit} class="space-y-6 max-w-2xl">
		<div class="bg-white p-6 rounded-ghost shadow-ghost-1 border border-ghost-border">
			<h2 class="text-xl font-semibold text-ghost-darkgrey mb-4">Globale Einstellungen</h2>
			<div class="space-y-4">
				<SelectInput id="settings-theme" label="Theme" options={themeOptions} bind:value={theme} />
			</div>
		</div>

		<div class="bg-white p-6 rounded-ghost shadow-ghost-1 border border-ghost-border">
			<h2 class="text-xl font-semibold text-ghost-darkgrey mb-4">Hero-Block bearbeiten</h2>
			<div class="space-y-4">
				<TextInput
					id="hero-title"
					label="Titel (H1)"
					placeholder="Dein Hero Titel"
					bind:value={title}
				/>

				<TextInput
					id="hero-subtitle"
					label="Untertitel"
					placeholder="Dein Untertitel"
					bind:value={subtitle}
				/>
			</div>
		</div>

		<div class="bg-white p-6 rounded-ghost shadow-ghost-1 border border-ghost-border">
			<h2 class="text-xl font-semibold text-ghost-darkgrey mb-4">Leistungen bearbeiten</h2>
			<ListRepeater bind:items={services} />
		</div>

		<div
			class="pt-2 sticky bottom-4 z-10 bg-ghost-whitegrey/80 backdrop-blur-sm p-4 rounded-ghost flex justify-end shadow-sm"
		>
			<button
				type="submit"
				disabled={isSaving}
				class="bg-primary hover:bg-primary-dark text-white px-6 py-2 rounded-ghost font-medium transition-colors disabled:opacity-50 disabled:cursor-not-allowed shadow-md"
			>
				{buttonText}
			</button>
		</div>
	</form>
</div>
