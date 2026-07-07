<script lang="ts">
	let {
		id,
		label,
		value = $bindable(),
		error,
		placeholder = ''
	}: {
		id: string;
		label?: string;
		value: string;
		error?: string;
		placeholder?: string;
	} = $props();
</script>

<div class="flex flex-col space-y-1 mb-4">
	{#if label}
		<label for={id} class="text-[1.3rem] font-semibold text-ghost-darkgrey">
			{label}
		</label>
	{/if}
	<input
		{id}
		type="text"
		{placeholder}
		bind:value
		class="h-[36px] px-3 py-2 text-[1.5rem] bg-white border border-ghost-whitegrey rounded-ghost shadow-sm transition-shadow outline-none text-ghost-black placeholder-ghost-midlightgrey focus:border-ghost-midgrey focus:ring-1 focus:ring-ghost-midgrey {error
			? 'border-error focus:border-error focus:ring-error'
			: ''}"
	/>
	{#if error}
		<p class="text-[1.3rem] text-error mt-1">{error}</p>
	{/if}
</div>
