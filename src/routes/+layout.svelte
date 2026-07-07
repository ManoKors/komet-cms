<script lang="ts">
	import '../app.css';

	let { children } = $props();
</script>

<div class="flex min-h-screen bg-ghost-bg font-sans text-ghost-darkgrey">
	<!-- Sidebar -->
	<aside
		class="fixed inset-y-0 left-0 w-[280px] bg-white border-r border-ghost-border z-20 flex flex-col"
	>
		<div class="p-ghost-side border-b border-ghost-border">
			<!-- Placeholder for Logo/Title -->
			<h1 class="text-[2rem] font-bold tracking-tight">Komet CMS</h1>
		</div>
		<nav class="flex-1 overflow-y-auto p-4 space-y-2 text-[1.4rem]">
			<!-- Sidebar Placeholders -->
			<div
				class="px-4 py-2 font-semibold text-ghost-midgrey uppercase tracking-wider text-[1.2rem]"
			>
				Main
			</div>
			<a href="/" class="block px-4 py-2 rounded-ghost hover:bg-ghost-whitegrey transition-colors">
				Dashboard
			</a>
			<a
				href="/inhalte"
				class="block px-4 py-2 rounded-ghost hover:bg-ghost-whitegrey transition-colors"
			>
				Inhalte
			</a>
		</nav>
	</aside>

	<!-- Main Content Area -->
	<main class="flex-1 ml-[280px] min-h-screen relative">
		<!-- Ghost Canvas -->
		<div class="gh-canvas w-full max-w-ghost-content mx-auto p-ghost-side py-12 min-h-full">
			<div class="bg-white rounded-ghost shadow-ghost-1 p-8 min-h-[calc(100vh-6rem)]">
				{@render children()}
			</div>
		</div>
	</main>
</div>
