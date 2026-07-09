import type { PageLoad } from './$types';

export const load: PageLoad = async ({ fetch }) => {
	let tenants = [];

	try {
		const res = await fetch('http://localhost:8080/api/v1/tenants');
		if (res.ok) {
			tenants = await res.json();
		}
	} catch (error) {
		console.error('Failed to fetch tenants:', error);
	}

	return {
		tenants
	};
};
