<script lang="ts">
	import TextInput from '$lib/components/TextInput.svelte';
	import { toast } from '$lib/stores/toast.svelte';

	let { data } = $props();

	let tenants = $state<any[]>([]);
	let newTenantName = $state('');
	let isCreating = $state(false);

	$effect(() => {
		tenants = data.tenants;
	});

	async function createTenant(event: Event) {
		event.preventDefault();
		if (!newTenantName.trim()) return;

		isCreating = true;
		try {
			const res = await fetch('http://localhost:8080/api/v1/tenants', {
				method: 'POST',
				headers: {
					'Content-Type': 'application/json'
				},
				body: JSON.stringify({ name: newTenantName.trim() })
			});

			if (res.ok) {
				const newTenant = await res.json();
				tenants = [...tenants, newTenant];
				newTenantName = '';
				toast.show('Mandant erfolgreich angelegt!', 'success');
			} else {
				toast.show('Fehler beim Anlegen des Mandanten.', 'error');
			}
		} catch (error) {
			console.error(error);
			toast.show('Ein unerwarteter Fehler ist aufgetreten.', 'error');
		} finally {
			isCreating = false;
		}
	}
</script>

<svelte:head>
	<title>Komet CMS - Mandanten-Übersicht</title>
</svelte:head>

<div class="max-w-ghost-content mx-auto mt-10 p-ghost-side">
	<h1 class="text-3xl font-bold text-ghost-black mb-6">Mandanten</h1>

	<div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6 mb-12">
		{#each tenants as tenant (tenant.id)}
			<a
				href={`/tenant/${tenant.id}`}
				class="bg-white p-6 rounded-ghost shadow-ghost-sm border border-ghost-border hover:shadow-ghost-md hover:border-ghost-midgrey transition-all flex flex-col justify-center h-32 cursor-pointer group"
			>
				<h2 class="text-xl font-bold text-ghost-black group-hover:text-primary transition-colors">{tenant.name}</h2>
				<span class="text-sm text-ghost-midgrey mt-2 font-mono truncate">{tenant.id}</span>
			</a>
		{:else}
			<div class="col-span-full p-8 text-center text-ghost-midgrey bg-white rounded-ghost shadow-ghost-sm border border-ghost-border">
				Keine Mandanten gefunden. Lege deinen ersten Mandanten an!
			</div>
		{/each}
	</div>

	<div class="bg-white p-6 rounded-ghost shadow-ghost-sm border border-ghost-border max-w-lg">
		<h2 class="text-xl font-semibold text-ghost-darkgrey mb-4">Neuen Mandanten anlegen</h2>
		<form onsubmit={createTenant} class="space-y-4">
			<TextInput
				id="new-tenant-name"
				label="Name des Mandanten"
				placeholder="z.B. Zahnarzt Praxis Müller"
				bind:value={newTenantName}
			/>
			<button
				type="submit"
				disabled={isCreating || !newTenantName.trim()}
				class="bg-primary hover:bg-primary-hover text-white px-6 py-2 rounded-ghost font-medium transition-colors disabled:opacity-50 disabled:cursor-not-allowed shadow-ghost-sm w-full flex items-center justify-center"
			>
				{#if isCreating}
					<svg
						class="animate-spin -ml-1 mr-2 h-4 w-4 text-white"
						xmlns="http://www.w3.org/2000/svg"
						fill="none"
						viewBox="0 0 24 24"
					>
						<circle class="opacity-25" cx="12" cy="12" r="10" stroke="currentColor" stroke-width="4"
						></circle>
						<path
							class="opacity-75"
							fill="currentColor"
							d="M4 12a8 8 0 018-8V0C5.373 0 0 5.373 0 12h4zm2 5.291A7.962 7.962 0 014 12H0c0 3.042 1.135 5.824 3 7.938l3-2.647z"
						></path>
					</svg>
				{/if}
				Mandant anlegen
			</button>
		</form>
	</div>
</div>
