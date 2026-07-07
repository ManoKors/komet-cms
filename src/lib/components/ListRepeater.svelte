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
			class="bg-white p-4 rounded-ghost shadow-ghost-sm flex items-start border border-ghost-border gap-4 group"
		>
			<div
				class="cursor-grab active:cursor-grabbing text-ghost-midlightgrey hover:text-ghost-darkgrey transition-colors mt-2"
			>
				<svg
					xmlns="http://www.w3.org/2000/svg"
					width="20"
					height="20"
					viewBox="0 0 24 24"
					fill="none"
					stroke="currentColor"
					stroke-width="2"
					stroke-linecap="round"
					stroke-linejoin="round"
				>
					<circle cx="9" cy="5" r="1" />
					<circle cx="9" cy="12" r="1" />
					<circle cx="9" cy="19" r="1" />
					<circle cx="15" cy="5" r="1" />
					<circle cx="15" cy="12" r="1" />
					<circle cx="15" cy="19" r="1" />
				</svg>
			</div>
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
			<div class="flex mt-2">
				<button
					type="button"
					class="text-ghost-midlightgrey hover:text-error transition-colors p-1 rounded-ghost opacity-0 group-hover:opacity-100 focus:opacity-100"
					onclick={() => handleDelete(item.id)}
					aria-label="Löschen"
					title="Löschen"
				>
					<svg
						xmlns="http://www.w3.org/2000/svg"
						width="20"
						height="20"
						viewBox="0 0 24 24"
						fill="none"
						stroke="currentColor"
						stroke-width="2"
						stroke-linecap="round"
						stroke-linejoin="round"
					>
						<path d="M3 6h18" />
						<path d="M19 6v14c0 1-1 2-2 2H7c-1 0-2-1-2-2V6" />
						<path d="M8 6V4c0-1 1-2 2-2h4c1 0 2 1 2 2v2" />
						<line x1="10" y1="11" x2="10" y2="17" />
						<line x1="14" y1="11" x2="14" y2="17" />
					</svg>
				</button>
			</div>
		</div>
	{/each}

	<div class="mt-2">
		<button
			type="button"
			class="text-ghost-midgrey hover:text-ghost-black hover:bg-ghost-whitegrey px-3 py-2 rounded-ghost transition-colors text-[1.4rem] font-medium flex items-center gap-2"
			onclick={handleAdd}
		>
			<svg
				xmlns="http://www.w3.org/2000/svg"
				width="16"
				height="16"
				viewBox="0 0 24 24"
				fill="none"
				stroke="currentColor"
				stroke-width="2"
				stroke-linecap="round"
				stroke-linejoin="round"
			>
				<line x1="12" y1="5" x2="12" y2="19" />
				<line x1="5" y1="12" x2="19" y2="12" />
			</svg>
			Element hinzufügen
		</button>
	</div>
</div>
