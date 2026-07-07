<script lang="ts">
	import TextInput from '$lib/components/TextInput.svelte';

	let { data } = $props();

	let title = $state(data.title);
	let subtitle = $state(data.subtitle);
	let buttonText = $state('Speichern');
	let isSaving = $state(false);

	async function handleSubmit(event: Event) {
		event.preventDefault();
		isSaving = true;
		buttonText = 'Speichert...';

		try {
			const response = await fetch('http://localhost:8080/api/v1/content/tenant-123', {
				method: 'POST',
				headers: {
					'Content-Type': 'application/json'
				},
				body: JSON.stringify({
					title,
					subtitle
				})
			});

			if (response.ok) {
				buttonText = 'Gespeichert!';
				setTimeout(() => {
					buttonText = 'Speichern';
				}, 2000);
			} else {
				buttonText = 'Fehler beim Speichern';
				setTimeout(() => {
					buttonText = 'Speichern';
				}, 2000);
			}
		} catch (error) {
			console.error('Fetch error:', error);
			buttonText = 'Fehler beim Speichern';
			setTimeout(() => {
				buttonText = 'Speichern';
			}, 2000);
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

	<div class="bg-white p-6 rounded-ghost shadow-ghost-1 border border-ghost-border max-w-2xl">
		<h2 class="text-xl font-semibold text-ghost-darkgrey mb-4">Hero-Block bearbeiten</h2>
		<form onsubmit={handleSubmit} class="space-y-4">
			<TextInput id="hero-title" label="Titel (H1)" placeholder="Dein Hero Titel" bind:value={title} />

			<TextInput id="hero-subtitle" label="Untertitel" placeholder="Dein Untertitel" bind:value={subtitle} />

			<div class="pt-2">
				<button
					type="submit"
					disabled={isSaving}
					class="bg-primary hover:bg-primary-dark text-white px-4 py-2 rounded-ghost font-medium transition-colors disabled:opacity-50 disabled:cursor-not-allowed"
				>
					{buttonText}
				</button>
			</div>
		</form>
	</div>
</div>
