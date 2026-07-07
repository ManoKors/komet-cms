<script lang="ts">
	import TextInput from '$lib/components/TextInput.svelte';

	export type ListItem = {
		id: string;
		title: string;
		description: string;
	};

	let { items = $bindable() }: { items: ListItem[] } = $props();

	function handleAdd() {
		items.push({
			id: crypto.randomUUID(),
			title: '',
			description: ''
		});
	}

	function handleDelete(id: string) {
		items = items.filter((item) => item.id !== id);
	}
</script>

<div class="flex flex-col gap-4">
	{#each items as item, i (item.id)}
		<div
			class="bg-white p-4 rounded-ghost shadow-ghost-1 flex justify-between items-start border border-ghost-border gap-4"
		>
			<div class="flex-1">
				<TextInput
					id="item-title-{item.id}"
					placeholder="Titel der Leistung"
					bind:value={items[i].title}
				/>
				<TextInput
					id="item-description-{item.id}"
					placeholder="Beschreibung der Leistung"
					bind:value={items[i].description}
				/>
			</div>
			<div class="flex gap-3 mt-2">
				<button
					type="button"
					class="text-sm font-medium text-ghost-midgrey hover:text-error transition-colors"
					onclick={() => handleDelete(item.id)}
				>
					Löschen
				</button>
			</div>
		</div>
	{/each}

	<div class="mt-2">
		<button
			type="button"
			class="text-ghost-midgrey hover:text-ghost-black hover:bg-ghost-whitegrey px-3 py-2 rounded-ghost transition-colors text-sm font-medium"
			onclick={handleAdd}
		>
			+ Element hinzufügen
		</button>
	</div>
</div>
