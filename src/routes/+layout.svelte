<script lang="ts">
	import '../app.css';
	import { toast } from '$lib/stores/toast.svelte';
	import { page } from '$app/stores';

	let { children } = $props();
</script>

{#if toast.visible}
	<div
		class="fixed bottom-6 right-6 z-50 px-6 py-3 rounded-ghost shadow-ghost-md text-white font-medium transition-all duration-300 ease-in-out {toast.type ===
		'success'
			? 'bg-primary'
			: 'bg-error'}"
	>
		{toast.message}
	</div>
{/if}

<div class="flex min-h-screen bg-ghost-bg font-sans text-ghost-darkgrey">
	<!-- Sidebar -->
	<aside
		class="fixed inset-y-0 left-0 w-[280px] bg-white border-r border-ghost-border z-20 flex flex-col"
	>
		<div class="p-ghost-side border-b border-ghost-border">
			<!-- Placeholder for Logo/Title -->
			<h1 class="text-[2rem] font-bold tracking-tight text-ghost-black">Komet CMS</h1>
		</div>
		<nav class="flex-1 overflow-y-auto p-4 space-y-2 text-[1.4rem]">
			{#if $page.params.tenantId}
				<a
					href="/"
					class="block px-4 py-2 text-[1.2rem] text-ghost-midgrey hover:text-ghost-black hover:bg-ghost-whitegrey rounded-ghost transition-colors mb-4 flex items-center"
				>
					<svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="mr-2"><path d="m15 18-6-6 6-6"/></svg>
					Zurück zu allen Mandanten
				</a>

				<div
					class="px-4 py-2 font-semibold text-ghost-midgrey uppercase tracking-wider text-[1.2rem]"
				>
					Mandant Menu
				</div>
				<a href={`/tenant/${$page.params.tenantId}`} class="block px-4 py-2 rounded-ghost hover:bg-ghost-whitegrey transition-colors">
					Dashboard
				</a>
				<a
					href={`/tenant/${$page.params.tenantId}/inhalte`}
					class="block px-4 py-2 rounded-ghost hover:bg-ghost-whitegrey transition-colors"
				>
					Inhalte
				</a>
			{:else}
				<div
					class="px-4 py-2 font-semibold text-ghost-midgrey uppercase tracking-wider text-[1.2rem]"
				>
					System
				</div>
				<a href="/" class="block px-4 py-2 rounded-ghost hover:bg-ghost-whitegrey transition-colors">
					Mandanten-Übersicht
				</a>
			{/if}
		</nav>
	</aside>

	<!-- Main Content Area -->
	<main class="flex-1 ml-[280px] min-h-screen relative">
		<!-- Ghost Canvas - Seamless background without the boxed wrapper -->
		<div class="gh-canvas w-full max-w-ghost-content mx-auto p-ghost-side py-12 min-h-full">
			{@render children()}
		</div>
	</main>
</div>
