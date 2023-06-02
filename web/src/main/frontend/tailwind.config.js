/** @type {import('tailwindcss').Config} */
module.exports = {
    content: ["../resources/templates/**/*.{html,js}"], // it will be explained later
    important: true,
    theme: {
        extend: {},
        colors: {
            'primary': '#76279B',
            'tertiary': '#C9C9C9',
            'text-primary': '#474747',
            'text-secondary': '#ABAAAA',
            'info': '#3578E5',
            'background': '#F5F5F5',
            'white': '#fff',
            'dark': '#302F2F',
            'dark-primary': '#682288',

            'danger': '#dc3545',
            'warning': '#ffc107',
            'success': '#4BB543',

            'shadow': 'rgba(75,75,75,0.5)'
        }
    },
    plugins: [],
}