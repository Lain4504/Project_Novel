// tailwind.config.js
export default {
    darkMode: 'class', // Enable dark mode using the 'class' strategy
    content: [
        "./index.html",
        "./src/**/*.{vue,js,ts,jsx,tsx}",
    ],
    theme: {
        extend: {
            typography: (theme) => ({
                DEFAULT: {
                    css: {
                        '--tw-prose-bullets': theme('colors.pink.500'),
                        li: {
                            p: {
                                margin: 0,
                            }
                        }
                    },
                },
            }),
        },
    },
    plugins: [
        require('@tailwindcss/typography'),
    ],
}