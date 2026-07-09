import type { PageLoad } from './$types';

export const load: PageLoad = async ({ fetch, params }) => {
	let title = '';
	let subtitle = '';
	let services = [];
	let theme = 'minimal-light';
	const tenantId = params.tenantId;

	try {
		const [heroRes, servicesRes, settingsRes] = await Promise.all([
			fetch(`http://localhost:8080/api/v1/content/${tenantId}/hero`),
			fetch(`http://localhost:8080/api/v1/content/${tenantId}/services`),
			fetch(`http://localhost:8080/api/v1/content/${tenantId}/settings`)
		]);

		if (heroRes.ok) {
			const heroResult = await heroRes.json();
			if (heroResult.status === 'success' && heroResult.data) {
				title = heroResult.data.title || '';
				subtitle = heroResult.data.subtitle || '';
			}
		}

		if (servicesRes.ok) {
			const servicesResult = await servicesRes.json();
			if (servicesResult.status === 'success' && servicesResult.data && servicesResult.data.items) {
				services = servicesResult.data.items;
			}
		}

		if (settingsRes.ok) {
			const settingsResult = await settingsRes.json();
			if (settingsResult.status === 'success' && settingsResult.data && settingsResult.data.theme) {
				theme = settingsResult.data.theme;
			}
		}
	} catch (error) {
		console.error('Failed to fetch content:', error);
	}

	return {
		tenantId,
		title,
		subtitle,
		services,
		theme
	};
};
