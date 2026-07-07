<script lang="ts">
	let {
		id,
		label,
		value = $bindable(),
		options = [],
		error
	}: {
		id: string;
		label?: string;
		value: string;
		options: { value: string; label: string }[];
		error?: string;
	} = $props();
</script>

<div class="flex flex-col space-y-1 mb-4">
	{#if label}
		<label for={id} class="text-[1.3rem] font-semibold text-ghost-darkgrey">
			{label}
		</label>
	{/if}
	<select
		{id}
		bind:value
		class="h-[40px] px-3 py-2 text-[1.5rem] bg-white border border-ghost-lightgrey rounded-ghost shadow-ghost-sm transition-shadow outline-none text-ghost-black hover:border-ghost-midgrey focus:border-ghost-black focus:ring-1 focus:ring-ghost-black cursor-pointer {error
			? 'border-error focus:border-error focus:ring-error'
			: ''}"
	>
		{#each options as option (option.value)}
			<option value={option.value}>{option.label}</option>
		{/each}
	</select>
	{#if error}
		<p class="text-[1.3rem] text-error mt-1">{error}</p>
	{/if}
</div>
