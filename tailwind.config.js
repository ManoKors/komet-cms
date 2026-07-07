/** @type {import('tailwindcss').Config} */
import colors from 'tailwindcss/colors';

export default {
	content: ['./src/**/*.{html,js,svelte,ts}'],
	theme: {
		extend: {
			colors: {
				primary: { DEFAULT: colors.zinc[900], hover: colors.black },
				ghost: {
					black: colors.zinc[900],
					darkgrey: colors.zinc[700],
					middarkgrey: colors.zinc[500],
					midgrey: colors.zinc[400],
					midlightgrey: colors.zinc[300],
					lightgrey: colors.zinc[200],
					whitegrey: colors.zinc[100],
					bg: colors.zinc[50],
					border: colors.zinc[200]
				},
				error: colors.red[500]
			},
			fontFamily: {
				sans: [
					'Geist',
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
			borderRadius: { ghost: '8px' },
			boxShadow: {
				'ghost-sm': '0 1px 2px 0 rgba(0, 0, 0, 0.05)',
				'ghost-md': '0 4px 6px -1px rgba(0, 0, 0, 0.05), 0 2px 4px -1px rgba(0, 0, 0, 0.03)',
				'ghost-lg': '0 10px 15px -3px rgba(0, 0, 0, 0.05), 0 4px 6px -2px rgba(0, 0, 0, 0.03)'
			},
			spacing: { 'ghost-side': '24px' },
			maxWidth: { 'ghost-content': '1200px' }
		}
	},
	plugins: []
};
