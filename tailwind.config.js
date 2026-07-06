/** @type {import('tailwindcss').Config} */
export default {
	content: ['./src/**/*.{html,js,svelte,ts}'],
	theme: {
		extend: {
			colors: {
				primary: { DEFAULT: '#30cf43', light: '#59d968', dark: '#1eb830' },
				ghost: {
					black: '#15171A',
					darkgrey: '#394047',
					middarkgrey: '#626D79',
					midgrey: '#7C8B9A',
					midlightgrey: '#ABB4BE',
					lightgrey: '#CED4D9',
					whitegrey: '#EBEEF0',
					bg: '#f5f6f6',
					border: '#EBEEF0'
				},
				error: '#f50b23'
			},
			fontFamily: {
				sans: [
					'Inter',
					'-apple-system',
					'BlinkMacSystemFont',
					'"Segoe UI"',
					'Roboto',
					'Oxygen',
					'Ubuntu',
					'"Droid Sans"',
					'"Helvetica Neue"',
					'sans-serif'
				],
				mono: ['Consolas', '"Liberation Mono"', 'Menlo', 'Courier', 'monospace']
			},
			borderRadius: { ghost: '6px' },
			boxShadow: {
				'ghost-1':
					'0 0 1px rgba(0,0,0,.14), 0 1px 6px rgba(0,0,0,0.05), 0 6px 10px -8px rgba(0,0,0,.14)',
				'ghost-2': '0 0 1px rgba(0,0,0,.05), 0 5px 18px rgba(0,0,0,.08)',
				'ghost-3': '0 0 1px rgba(0,0,0,.05), 0 8px 28px rgba(0,0,0,.12)'
			},
			spacing: { 'ghost-side': '24px' },
			maxWidth: { 'ghost-content': '1200px' }
		}
	},
	plugins: []
};
