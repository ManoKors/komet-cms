import type { PageLoad } from './$types';

export const load: PageLoad = async ({ fetch }) => {
    try {
        const response = await fetch('http://localhost:8080/api/v1/content/tenant-123');

        if (response.ok) {
            const result = await response.json();

            if (result.status === 'success' && result.data) {
                return {
                    title: result.data.title || '',
                    subtitle: result.data.subtitle || ''
                };
            }
        }

        // Handle 404 or missing data gracefully
        return {
            title: '',
            subtitle: ''
        };
    } catch (error) {
        console.error('Failed to fetch hero content:', error);
        return {
            title: '',
            subtitle: ''
        };
    }
};
