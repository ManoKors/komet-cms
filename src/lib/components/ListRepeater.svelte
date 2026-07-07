<script lang="ts">
	type ListItem = {
		id: string;
		title: string;
		description: string;
	};

	let items = $state<ListItem[]>([
		{
			id: '1',
			title: 'Physiotherapie',
			description: 'Klassische Krankengymnastik zur Wiederherstellung der Beweglichkeit.'
		},
		{
			id: '2',
			title: 'Manuelle Therapie',
			description: 'Spezielle Handgriffe zur Schmerzlinderung und Mobilisation.'
		}
	]);

	function handleAdd() {
		console.log('Add new item');
	}

	function handleEdit(id: string) {
		console.log('Edit item:', id);
	}

	function handleDelete(id: string) {
		console.log('Delete item:', id);
		items = items.filter((item) => item.id !== id);
	}
</script>

<div class="flex flex-col gap-4">
	{#each items as item (item.id)}
		<div
			class="bg-white p-4 rounded-ghost shadow-ghost-1 flex justify-between items-center border border-ghost-border"
		>
			<div>
				<h3 class="font-semibold text-ghost-black">{item.title}</h3>
				<p class="text-sm text-ghost-midgrey mt-1">{item.description}</p>
			</div>
			<div class="flex gap-3">
				<button
					type="button"
					class="text-sm font-medium text-ghost-midgrey hover:text-ghost-black transition-colors"
					onclick={() => handleEdit(item.id)}
				>
					Bearbeiten
				</button>
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
