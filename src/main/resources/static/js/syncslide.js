/**
 * SyncSlide — Common JS utilities
 * Shared polling and API helper functions
 */

const SyncSlide = {
    /**
     * Fetch current slide state
     */
    async getSlide() {
        const res = await fetch('/api/slide');
        return res.json();
    },

    /**
     * Set slide to specific number
     */
    async setSlide(n) {
        const res = await fetch('/api/slide', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ slide: n })
        });
        return res.json();
    },

    /**
     * Go to next slide
     */
    async nextSlide() {
        const res = await fetch('/api/slide/next', { method: 'POST' });
        return res.json();
    },

    /**
     * Go to previous slide
     */
    async prevSlide() {
        const res = await fetch('/api/slide/prev', { method: 'POST' });
        return res.json();
    },

    /**
     * Get demo state
     */
    async getDemo() {
        const res = await fetch('/api/demo');
        return res.json();
    },

    /**
     * Set demo mode
     */
    async setDemo(mode, url) {
        const body = { mode };
        if (url) body.url = url;
        const res = await fetch('/api/demo', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(body)
        });
        return res.json();
    },

    /**
     * Get full config
     */
    async getConfig() {
        const res = await fetch('/api/config');
        return res.json();
    },

    /**
     * Toggle browser fullscreen
     */
    toggleFullscreen() {
        if (!document.fullscreenElement) {
            document.documentElement.requestFullscreen().catch(() => {});
        } else {
            document.exitFullscreen();
        }
    },

    /**
     * Build slide image URL
     */
    slideUrl(n) {
        return `/presentation/Slide${n}.PNG`;
    }
};
