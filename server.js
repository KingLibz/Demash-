const express = require('express');
const app = express();

app.use(express.json());
app.use(express.urlencoded({ extended: true }));

// 1. HARDENED CORE SYSTEM ENVIRONMENT CONFIGURATION
const SYSTEM_CONFIG = {
    PORT: process.env.PORT || 3000,
    NORTON_BASE: "K17412, Katanga, Norton, Zimbabwe",
    WHATSAPP_PHONE_NUMBER: "+263784219178",
    META_VERIFY_TOKEN: process.env.META_VERIFY_TOKEN || "DemashGlobalSecureToken2026",
    META_ACCESS_TOKEN: process.env.META_ACCESS_TOKEN || "YOUR_PERMANENT_ACCESS_TOKEN",
    PHONE_NUMBER_ID: process.env.PHONE_NUMBER_ID || "YOUR_PHONE_NUMBER_ID",
    AHM_REF_TOKEN: "mnpixfvf",
    PRODUCTION_DOMAIN: "demashglobal.live"
};

// 2. DYNAMIC REAL-TIME INVENTORY & REAL ESTATE DATA POOL
let dynamicInventoryPool = [
    { id: "ahm_001", type: "machinery", name: "Deformed Bar Bender", stock: 1, url: "https://example.com" },
    { id: "prop_002", type: "real_estate", name: "Contemporary Turnkey Villa", stock: 1, url: "https://example.com" },
    { id: "clothing_003", type: "apparel", name: "Heavyweight Sweatpants", stock: 15, url: "https://example.com" }
];

// 3. CASE-INSENSITIVE EXTRACTION, URL PARSING & AUTOMATED BOT FILTERS
function parseIncomingLeadData(rawMessageText) {
    if (!rawMessageText) return { score: 0, extractedURLs: [] };

    // Strict sanitisation: case-insensitivity mapping handling raw trailing lines/spaces
    const processedText = rawMessageText.trim().toLowerCase();
    
    // Intercepts and isolates embedded URLs seamlessly even mid-sentence without spacing
    const urlExtractionPattern = /(https?:\/\/[^\s]+)/gi;
    const extractedURLs = rawMessageText.match(urlExtractionPattern) || [];

    // Safety drop block: Auto-reject out-of-office bot lines or voicemail auto-replies
    const botPatterns = /(out of office|automated response|voicemail|auto-reply|vacation notice)/i;
    if (botPatterns.test(processedText)) {
        console.log("⚠️ Out-of-office bot trace flagged via validation filters. Dropping connection.");
        return { score: 0, extractedURLs: [] };
    }

    let intentScore = 0;
    // Core buyer action keywords & transaction signals
    const strongSignals = /(buy|order|price|how much|stock|ecocash|cost|quote|delivery|shoes|tees|sweatpants)/i;
    const immediateSignals = /(want to order|confirm stock|payment details|send proof|transfer)/i;

    if (strongSignals.test(processedText)) intentScore += 50;
    if (immediateSignals.test(processedText)) intentScore += 50;

    return {
        score: Math.min(intentScore, 100),
        extractedURLs: extractedURLs
    };
}

// 4. BEHAVIORAL ECONOMICS TRIGGER: AUTOMATIC SCARCITY TOGGLE
function markProductAsSold(productId) {
    console.log(`🔄 Updating live asset streams. Inventory ID state modification check: ${productId}`);
    // Modifies stock instantly; pushes item offline if out of stock or sold out
    dynamicInventoryPool = dynamicInventoryPool.map(item => {
        if (item.id === productId) {
            return { ...item, stock: 0 };
        }
        return item;
    });
}

// 5. OUTBOUND WHATSAPP NOTIFICATION DISPATCHER (META GRAPH API)
async function routeNotificationToWhatsApp(messagePayload) {
    const url = `https://facebook.com{SYSTEM_CONFIG.PHONE_NUMBER_ID}/messages`;
    
    try {
        console.log(`📡 Dispatching 100% verified intent deal notification to +${SYSTEM_CONFIG.WHATSAPP_PHONE_NUMBER}...`);
        
        // Unleash the network fetch once your live Meta Graph tokens are applied
        /*
        const response = await fetch(url, {
            method: 'POST',
            headers: {
                'Authorization': `Bearer ${SYSTEM_CONFIG.META_ACCESS_TOKEN}`,
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                messaging_product: "whatsapp",
                to: SYSTEM_CONFIG.WHATSAPP_PHONE_NUMBER,
                type: "text",
                text: { body: messagePayload }
            })
        });
        const result = await response.json();
        console.log("✅ Meta API Broadcast Success:", result);
        */
        console.log(`✉️ LIVE BACKEND BROADCAST EMULATION TRACE:\n${messagePayload}`);
    } catch (error) {
        console.error("❌ Outbound transaction fail via Meta API endpoint:", error.message);
    }
}

// 6. MULTI-VENDOR GATEWAY ROUTER (TARGETED REFERRAL TRACKING)
app.get('/gateways/redirect', (req, res) => {
    const targetVendor = (req.query.vendor || '').toLowerCase().trim();
    console.log(`📡 Logged affiliate request verification. Access signature trace: ${SYSTEM_CONFIG.AHM_REF_TOKEN}`);
    
    // Limits your private tag exclusively to your single chosen vendor (AHM)
    if (targetVendor === 'ahm') {
        return res.redirect(`https://example.com{SYSTEM_CONFIG.AHM_REF_TOKEN}`);
    }
    
    // Unmatched vendor flows fallback safely to admin dashboard
    res.redirect(`https://${SYSTEM_CONFIG.PRODUCTION_DOMAIN}/admin`);
});

// 7. META DEVELOPER PORTAL WEBHOOK HANDSHAKE ENDPOINT
app.get('/webhook', (req, res) => {
    const mode = req.query['hub.mode'];
    const token = req.query['hub.verify_token'];
    const challenge = req.query['hub.challenge'];

    if (mode && token === SYSTEM_CONFIG.META_VERIFY_TOKEN) {
        console.log("✅ Webhook handshake verified successfully with Meta Developer Portal!");
        return res.status(200).send(challenge);
    }
    res.sendStatus(403);
});

// 8. INCOMING SALES WEBHOOK & BROADCAST AUTOMATION PIPELINE
app.post('/webhook', async (req, res) => {
    const messageObject = req.body.entry?.[0]?.changes?.[0]?.value?.messages?.[0];

    if (messageObject) {
        const buyerPhone = messageObject.from;
        const buyerMessage = messageObject.text?.body || "";
        
        // Core validation execution layer
        const analysis = parseIncomingLeadData(buyerMessage);
        console.log(`📊 Processing algorithmic intent matrices. Calculated Score: ${analysis.score}%`);

        // Routes traffic automatically only when score touches a perfect 100%
        if (analysis.score === 100) {
            let leadAlert = `🎯 DEMASH GLOBAL PRODUCTION SUITE ALERT!\n\n` +
                            `📱 Buyer Line: +${buyerPhone}\n` +
                            `💬 Message: "${buyerMessage}"\n` +
                            `💰 Status: 100% Validated Intent. Dynamic POS link tracking engaged.`;
            
            if (analysis.extractedURLs.length > 0) {
                leadAlert += `\n🔗 Extracted Destination URLs: ${analysis.extractedURLs.join(', ')}`;
            }

            await routeNotificationToWhatsApp(leadAlert);
        }
    }
    
    // Direct instant 200 OK payload confirmation back to Meta
    res.status(200).send('EVENT_RECEIVED');
});

// 9. DYNAMIC ADMINISTRATIVE PANEL VIEW
app.get('/admin', (req, res) => {
    // Dynamic Filter: Strips away sold properties or out-of-stock items instantly
    const availableItems = dynamicInventoryPool.filter(item => item.stock > 0);
    
    let catalogHTML = `<div class="catalog-grid">`;
    availableItems.forEach(item => {
        catalogHTML += `
            <div class="product-card" style="padding: 16px; border: 1px solid #334155; margin-bottom: 12px; border-radius: 8px; background: #1e293b;">
                <h3 style="margin: 0; color: #10b981;">${item.name}</h3>
                <p style="font-size: 11px; color: #94a3b8; margin: 6px 0;">Tracked Segment: ${item.type.toUpperCase()}</p>
                <p style="font-size: 12px; color: #cbd5e1; margin-bottom: 12px;">Carrying active partner POS commission tags.</p>
                <a href="${item.url}" style="font-size: 11px; color: #38bdf8; text-decoration: none; font-weight: bold;">View Dynamic Supplier Link →</a>
            </div>
        `;
    });
    catalogHTML += `</div>`;

    if (availableItems.length === 0) {
        catalogHTML = `<p style="color: #ef4444; font-weight: bold;">⚠️ All tracked supplier assets and real estate metrics are flagged as sold out.</p>`;
    }

    return res.send(`
      <!DOCTYPE html>
      <html>
      <head>
        <meta charset="utf-8">
        <title>Demash Global Engine</title>
        <style>
          body { background: #0f172a; font-family: system-ui, sans-serif; color: #fff; padding: 24px; }
          .container { max-width: 800px; margin: 0 auto; background: #1e293b; padding: 32px; border-radius: 12px; border: 1px solid #1e293b; }
          .brand-title { font-size: 20px; margin: 0 0 24px 0; color: #fff; border-bottom: 1px solid #334155; padding-bottom: 12px; } 
          .footer { font-size: 10px; color: #94a3b8; text-align: center; font-family: monospace; margin-top: 48px; line-height: 1.6; } 
        </style> 
      </head> 
      <body> 
        <div class="container"> 
          <h1 class="brand-title">Demash Global • Active Dynamic Pipeline</h1> 
          <div>${catalogHTML}</div> 
          <div class="footer">
            Workshop Base: ${SYSTEM_CONFIG.NORTON_BASE}.<br>
            Global Operations Automated • Secure Routing Active • AHM Trackers Operational.
          </div> 
        </div> 
      </body> 
      </html>
    `);
});

// 10. SOVEREIGN PROTECTION GLOBAL EXCEPTION FALLBACK
app.use((err, req, res, next) => { 
    console.error("⛔ Internal System Error Exception Caught:", err.stack);
    res.status(200).send("Sovereign Protection Active."); 
});

// START MONOLITH PROCESS
app.listen(SYSTEM_CONFIG.PORT, () => {
    console.log(`🚀 System Compiled Successfully. Domain: ${SYSTEM_CONFIG.PRODUCTION_DOMAIN} on Port ${SYSTEM_CONFIG.PORT}`);
});
