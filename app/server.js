const http = require('http');
const fs = require('fs');
const path = require('path');

const PORT = 3000;
const APK_PATH = '/app/applet/.build-outputs/app-debug.apk';
const FALLBACK_APK_PATH = '/app/applet/app/build/outputs/apk/debug/app-debug.apk';

function getApkFile() {
  if (fs.existsSync(APK_PATH)) return APK_PATH;
  if (fs.existsSync(FALLBACK_APK_PATH)) return FALLBACK_APK_PATH;
  return null;
}

const server = http.createServer((req, res) => {
  const url = req.url.split('?')[0];

  // Route: Direct APK Download
  if (url === '/download-apk' || url === '/app-debug.apk' || url === '/DemashPools.apk' || url === '/download') {
    const apkFile = getApkFile();
    if (!apkFile) {
      res.writeHead(404, { 'Content-Type': 'text/plain' });
      res.end('APK file is currently generating. Please wait a moment and try again.');
      return;
    }
    const stat = fs.statSync(apkFile);
    res.writeHead(200, {
      'Content-Type': 'application/vnd.android.package-archive',
      'Content-Disposition': 'attachment; filename="DemashPools_Zimbabwe.apk"',
      'Content-Length': stat.size,
      'Cache-Control': 'no-cache'
    });
    fs.createReadStream(apkFile).pipe(res);
    return;
  }

  // Route: Health check
  if (url === '/health' || url === '/ping') {
    res.writeHead(200, { 'Content-Type': 'application/json' });
    res.end(JSON.stringify({ status: 'ok', app: 'Demash Pools Zimbabwe' }));
    return;
  }

  // Route: Main Web Application & App Download Portal
  res.writeHead(200, { 'Content-Type': 'text/html; charset=utf-8' });
  res.end(`<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
  <title>Demash Pools Zimbabwe · Luxury Swimming Pools & Landscaping</title>
  <link rel="icon" href="data:image/svg+xml,<svg xmlns=%22http://www.w3.org/2000/svg%22 viewBox=%220 0 100 100%22><text y=%22.9em%22 font-size=%2290%22>🏊</text></svg>">
  <style>
    :root {
      --bg: #04141D;
      --surface: #0B2230;
      --surface-elevated: #112E40;
      --surface-highlight: #19415B;
      --gold: #E3B04B;
      --gold-dark: #C48D2A;
      --aqua: #06B6D4;
      --text: #F8FAFC;
      --text-muted: #94A3B8;
      --green: #10B981;
      --border: #1E3A52;
      --border-gold: rgba(227, 176, 75, 0.4);
    }
    * { box-sizing: border-box; margin: 0; padding: 0; font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Helvetica, Arial, sans-serif; }
    body { background-color: var(--bg); color: var(--text); padding-bottom: 60px; line-height: 1.5; }
    .container { max-width: 600px; margin: 0 auto; padding: 16px; }
    
    /* Top Bar */
    header { background: var(--surface); border-bottom: 1px solid var(--border); padding: 14px 16px; position: sticky; top: 0; z-index: 100; display: flex; align-items: center; justify-content: space-between; }
    .brand { display: flex; align-items: center; gap: 10px; }
    .logo-badge { background: var(--gold); color: var(--bg); font-weight: 900; font-size: 14px; width: 34px; height: 34px; border-radius: 8px; display: flex; align-items: center; justify-content: center; }
    .brand-title { font-weight: 800; font-size: 16px; color: var(--text); }
    .brand-sub { font-size: 10px; color: var(--gold); letter-spacing: 0.5px; }
    .call-btn { background: var(--gold); color: var(--bg); border: none; padding: 6px 12px; border-radius: 20px; font-weight: 700; font-size: 11px; text-decoration: none; display: inline-flex; align-items: center; gap: 4px; }

    /* Hero / App Download */
    .download-card { background: linear-gradient(145deg, var(--surface-elevated), var(--surface)); border: 1.5px solid var(--gold); border-radius: 20px; padding: 20px; margin-top: 14px; box-shadow: 0 10px 30px rgba(0,0,0,0.5); }
    .badge-row { display: flex; justify-content: space-between; align-items: center; margin-bottom: 12px; }
    .pill-android { background: var(--gold); color: var(--bg); font-size: 10px; font-weight: 800; padding: 3px 8px; border-radius: 6px; letter-spacing: 0.5px; }
    .pill-voucher { background: rgba(16, 185, 129, 0.15); color: var(--green); border: 1px solid var(--green); font-size: 10px; font-weight: 800; padding: 3px 8px; border-radius: 6px; }
    .hero-title { font-size: 21px; font-weight: 800; line-height: 1.25; margin-bottom: 8px; }
    .hero-desc { font-size: 13px; color: var(--text-muted); margin-bottom: 16px; }
    
    .btn-download-apk { display: flex; align-items: center; justify-content: center; gap: 8px; background: var(--gold); color: var(--bg); text-decoration: none; font-weight: 800; font-size: 15px; padding: 14px; border-radius: 12px; width: 100%; text-align: center; margin-bottom: 10px; box-shadow: 0 4px 15px rgba(227, 176, 75, 0.3); }
    .btn-share { display: flex; align-items: center; justify-content: center; gap: 8px; background: var(--surface-highlight); color: var(--aqua); text-decoration: none; font-weight: 700; font-size: 13px; padding: 10px; border-radius: 10px; width: 100%; border: 1px solid var(--border); }
    
    /* Stats Row */
    .stats-grid { display: grid; grid-template-columns: repeat(3, 1fr); gap: 8px; margin: 16px 0; }
    .stat-box { background: var(--surface); border: 1px solid var(--border); border-radius: 12px; padding: 12px 8px; text-align: center; }
    .stat-val { font-size: 18px; font-weight: 800; color: var(--gold); }
    .stat-lbl { font-size: 10px; color: var(--text-muted); margin-top: 2px; }

    /* Estimator Card */
    .card { background: var(--surface); border: 1px solid var(--border); border-radius: 16px; padding: 18px; margin-bottom: 16px; }
    .card-gold { border-color: var(--gold); }
    .section-eyebrow { font-size: 10px; font-weight: 800; color: var(--gold); letter-spacing: 1px; text-transform: uppercase; margin-bottom: 2px; }
    .section-title { font-size: 18px; font-weight: 800; color: var(--text); margin-bottom: 12px; }
    
    .slider-row { margin-bottom: 14px; }
    .slider-header { display: flex; justify-content: space-between; font-size: 13px; font-weight: 600; margin-bottom: 6px; }
    .slider-val { color: var(--gold); font-weight: 700; }
    input[type=range] { width: 100%; height: 6px; background: var(--surface-elevated); border-radius: 4px; accent-color: var(--gold); outline: none; }
    
    .price-box { background: var(--surface-elevated); border: 1px solid var(--border-gold); border-radius: 12px; padding: 16px; text-align: center; margin: 14px 0; }
    .price-lbl { font-size: 11px; font-weight: 700; color: var(--text-muted); letter-spacing: 0.5px; }
    .price-num { font-size: 32px; font-weight: 900; color: var(--gold); line-height: 1.1; margin: 4px 0; }
    .market-comp { font-size: 11px; color: var(--text-muted); text-decoration: line-through; }
    .save-badge { display: inline-block; background: rgba(16, 185, 129, 0.2); color: var(--green); border: 1px solid var(--green); padding: 3px 8px; border-radius: 6px; font-size: 11px; font-weight: 700; margin-top: 6px; }

    /* Zimbabwe Standards Table */
    .table-list { display: flex; flex-direction: column; gap: 8px; background: var(--surface-elevated); padding: 12px; border-radius: 12px; margin-top: 10px; }
    .table-item { display: flex; justify-content: space-between; align-items: flex-start; padding-bottom: 6px; border-bottom: 1px solid rgba(255,255,255,0.05); }
    .table-item:last-child { border-bottom: none; padding-bottom: 0; }
    .item-name { font-size: 12px; font-weight: 700; color: var(--text); }
    .item-sub { font-size: 10px; color: var(--text-muted); }
    .item-price { font-size: 11px; font-weight: 700; color: var(--gold); text-align: right; }

    /* Packages */
    .pkg-grid { display: flex; flex-direction: column; gap: 12px; margin-top: 10px; }
    .pkg-card { background: var(--surface-elevated); border: 1px solid var(--border); border-radius: 14px; padding: 14px; }
    .pkg-card.featured { border: 1.5px solid var(--gold); background: #133348; }
    .pkg-header { display: flex; justify-content: space-between; align-items: center; margin-bottom: 6px; }
    .pkg-name { font-size: 15px; font-weight: 800; color: var(--text); }
    .pkg-price { font-size: 16px; font-weight: 800; color: var(--gold); }
    .pkg-dim { font-size: 11px; color: var(--aqua); margin-bottom: 8px; }
    .pkg-list { font-size: 11px; color: var(--text-muted); list-style: none; line-height: 1.6; }
    .pkg-list li::before { content: "✓ "; color: var(--gold); font-weight: bold; }

    /* Buttons */
    .btn-wa { display: inline-flex; align-items: center; justify-content: center; gap: 6px; background: var(--green); color: #04141D; font-weight: 800; font-size: 13px; padding: 10px 16px; border-radius: 10px; text-decoration: none; width: 100%; margin-top: 8px; }

    /* Footer */
    footer { text-align: center; padding: 24px 16px; font-size: 11px; color: var(--text-muted); }
    .contact-pill { display: inline-block; background: var(--surface); padding: 8px 14px; border-radius: 20px; border: 1px solid var(--border); margin-top: 8px; color: var(--gold); font-weight: 700; }
  </style>
</head>
<body>

  <header>
    <div class="brand">
      <div class="logo-badge">DZ</div>
      <div>
        <div class="brand-title">Demash Pools</div>
        <div class="brand-sub">NORTON & HARARE · ZIMBABWE</div>
      </div>
    </div>
    <a href="tel:+263784219178" class="call-btn">📞 Call Eng. Liberman</a>
  </header>

  <div class="container">

    <!-- App Download Card -->
    <div class="download-card">
      <div class="badge-row">
        <span class="pill-android">📲 OFFICIAL ANDROID APP</span>
        <span class="pill-voucher">US$150 VOUCHER</span>
      </div>
      <h1 class="hero-title">World-Class Pools & Landscaping, Built for Zimbabwe.</h1>
      <p class="hero-desc">Download the official Demash Pools Android App to quote your yard in 10 seconds offline, view verified local projects, and claim US$150 off your new pool build deposit!</p>
      
      <a href="/download-apk" class="btn-download-apk" download="DemashPools_Zimbabwe.apk">
        ⬇️ Download Android App (APK 25MB)
      </a>

      <a href="https://wa.me/263784219178?text=Hi%20Demash%20Pools!%20I%20am%20claiming%20my%20US$150%20Construction%20Voucher%20(Promo:%20DZ-APP-150).%20Can%20you%20assist%20me%20with%20a%20pool%20quote?" class="btn-wa" target="_blank">
        💬 Claim US$150 Voucher via WhatsApp
      </a>

      <div style="margin-top: 10px;">
        <a href="https://wa.me/?text=%F0%9F%8F%8A%20Check%20out%20Demash%20Dzimbabwe%20Pools!%20World-class%20pools%20in%20Norton%20%26%20Harare.%20Get%20instant%2010s%20quotes%20with%2032%25%20savings,%2010-yr%20warranty,%20and%20US$150%20voucher:%20https://ais-dev-hovuo3yz75o2jlyzziavm3-161436339311.europe-west2.run.app" class="btn-share" target="_blank">
          🔗 Share with WhatsApp Groups & Family
        </a>
      </div>
    </div>

    <!-- Live Stats Grid -->
    <div class="stats-grid">
      <div class="stat-box">
        <div class="stat-val">32%</div>
        <div class="stat-lbl">Below Market Rates</div>
      </div>
      <div class="stat-box">
        <div class="stat-val">10-Yr</div>
        <div class="stat-lbl">Shell Warranty</div>
      </div>
      <div class="stat-box">
        <div class="stat-val">90 Min</div>
        <div class="stat-lbl">Fast Response</div>
      </div>
    </div>

    <!-- Interactive Instant Estimator -->
    <div class="card card-gold">
      <div class="section-eyebrow">Instant Estimator</div>
      <div class="section-title">Your Price in 10 Seconds</div>
      <p style="font-size: 12px; color: var(--text-muted); margin-bottom: 14px;">Move the sliders below — direct transparent costing for Zimbabwean soils.</p>

      <div class="slider-row">
        <div class="slider-header">
          <span>Pool Length</span>
          <span class="slider-val" id="len-disp">5.0 meters</span>
        </div>
        <input type="range" id="slider-len" min="3" max="15" step="0.5" value="5">
      </div>

      <div class="slider-row">
        <div class="slider-header">
          <span>Pool Width</span>
          <span class="slider-val" id="wid-disp">3.0 meters</span>
        </div>
        <input type="range" id="slider-wid" min="2" max="8" step="0.5" value="3">
      </div>

      <div class="price-box">
        <div class="price-lbl">ESTIMATED FULL BUILD (TURNKEY)</div>
        <div class="price-num" id="calc-price">US$6,500</div>
        <div class="market-comp">Harare competitor: <span id="calc-market">US$9,550</span></div>
        <div><span class="save-badge">You save ~US$<span id="calc-save">3,050</span> (32%)</span></div>
      </div>

      <a id="btn-quote-wa" href="https://wa.me/263784219178?text=Hi%20Demash%20Pools!%20I%20used%20your%20estimator%20for%20a%205x3m%20pool%20quoted%20at%20US$6,500.%20I%20would%20like%20a%20site%20visit." class="btn-wa" target="_blank">
        💬 Book Site Survey (US$50) on WhatsApp
      </a>
    </div>

    <!-- Zimbabwe Market Packaging & Standards -->
    <div class="card">
      <div class="section-eyebrow">Standards & Sizing</div>
      <div class="section-title">The Authentic Zimbabwe Way</div>
      <p style="font-size: 12px; color: var(--text-muted);">All materials computed strictly in standard Zimbabwean trade packaging:</p>

      <div class="table-list">
        <div class="table-item">
          <div>
            <div class="item-name">Cement Packaging</div>
            <div class="item-sub">50kg Bags PPC Surebuild 42.5R High-Strength</div>
          </div>
          <div class="item-price">US$10.50 / bag</div>
        </div>
        <div class="table-item">
          <div>
            <div class="item-name">Steel Rebar</div>
            <div class="item-sub">6-Meter Standard Sticks Y10 & Y12 Deformed Rebar</div>
          </div>
          <div class="item-price">US$8.50 / bar</div>
        </div>
        <div class="table-item">
          <div>
            <div class="item-name">River Sand & Quarry Stone</div>
            <div class="item-sub">m³ Washed Manyame River Sand & 19mm Blue Granite</div>
          </div>
          <div class="item-price">US$22/ton / US$18/m³</div>
        </div>
        <div class="table-item">
          <div>
            <div class="item-name">Plaster & Marbelite</div>
            <div class="item-sub">25kg Bags Marble Plaster (Super White & Sky Blue)</div>
          </div>
          <div class="item-price">US$18 / bag</div>
        </div>
        <div class="table-item">
          <div>
            <div class="item-name">Hydraulics & Filter Sand</div>
            <div class="item-sub">Class 9/12 50mm PVC (6m) & 50kg Graded Silica Sand</div>
          </div>
          <div class="item-price">2, 3 & 4-Bag Tanks</div>
        </div>
        <div class="table-item">
          <div>
            <div class="item-name">Accepted Currencies</div>
            <div class="item-sub">USD Cash (clean notes), EcoCash USD, Innbucks, Nostro, ZiG</div>
          </div>
          <div class="item-price">Zero Surcharge</div>
        </div>
      </div>
    </div>

    <!-- Pricing Packages -->
    <div class="card">
      <div class="section-eyebrow">Popular Packages</div>
      <div class="section-title">Fixed-Price Turnkey Builds</div>

      <div class="pkg-grid">
        <div class="pkg-card">
          <div class="pkg-header">
            <span class="pkg-name">Plunge Pool</span>
            <span class="pkg-price">US$3,500</span>
          </div>
          <div class="pkg-dim">3.2m × 2.0m · 2–3 weeks build</div>
          <ul class="pkg-list">
            <li>Compact gunite shell for townhouses & clusters</li>
            <li>Quality 0.75HP eco-pump & sand filter included</li>
            <li>Underwater LED glow light</li>
            <li>6 months free chemical care</li>
          </ul>
        </div>

        <div class="pkg-card featured">
          <div class="pkg-header">
            <span class="pkg-name">Family Favourite ⭐</span>
            <span class="pkg-price">US$6,500</span>
          </div>
          <div class="pkg-dim">5.0m × 3.0m · 3–5 weeks build</div>
          <ul class="pkg-list">
            <li>Monolithic gunite shell with dual rebar grid</li>
            <li>Tiled waterline & choice of white/blue marbelite</li>
            <li>1.0HP pump, sand filter & non-slip bullnose coping</li>
            <li>10-year structural shell guarantee</li>
          </ul>
        </div>

        <div class="pkg-card">
          <div class="pkg-header">
            <span class="pkg-name">Grand Resort</span>
            <span class="pkg-price">US$11,500</span>
          </div>
          <div class="pkg-dim">8.0m × 4.0m · Freeform luxury</div>
          <ul class="pkg-list">
            <li>Architectural freeform or rim-flow design</li>
            <li>Solar heating pre-plumbing included</li>
            <li>Natural granite waterfall / sheer descent integration</li>
            <li>Designer travertine paving & 6 mo white-glove aftercare</li>
          </ul>
        </div>
      </div>
    </div>

    <footer>
      <p><strong>Demash Dzimbabwe Pools (Pvt) Ltd</strong></p>
      <p>K17412 Katanga, Norton · Building Across Zimbabwe</p>
      <div class="contact-pill">Director Liberman Magaya: +263 78 421 9178</div>
      <p style="margin-top: 12px;">© 2026 Demash Pools · All Rights Reserved</p>
    </footer>

  </div>

  <script>
    const sLen = document.getElementById('slider-len');
    const sWid = document.getElementById('slider-wid');
    const lenDisp = document.getElementById('len-disp');
    const widDisp = document.getElementById('wid-disp');
    const calcPrice = document.getElementById('calc-price');
    const calcMarket = document.getElementById('calc-market');
    const calcSave = document.getElementById('calc-save');
    const btnQuote = document.getElementById('btn-quote-wa');

    function update() {
      const l = parseFloat(sLen.value);
      const w = parseFloat(sWid.value);
      lenDisp.textContent = l.toFixed(1) + ' meters';
      widDisp.textContent = w.toFixed(1) + ' meters';

      const area = l * w;
      // Gunite base: 4800, rate: 85/m2
      let price = Math.round(4800 + (area * 85));
      if (price < 4800) price = 4800;
      const market = Math.round(price / 0.68);
      const savings = market - price;

      calcPrice.textContent = 'US$' + price.toLocaleString();
      calcMarket.textContent = 'US$' + market.toLocaleString();
      calcSave.textContent = savings.toLocaleString();

      const text = 'Hi Demash Pools! I used your web estimator for a ' + l + 'm x ' + w + 'm pool estimated at US$' + price.toLocaleString() + ' (saving US$' + savings.toLocaleString() + '). I would like to schedule a site survey.';
      btnQuote.href = 'https://wa.me/263784219178?text=' + encodeURIComponent(text);
    }

    sLen.addEventListener('input', update);
    sWid.addEventListener('input', update);
    update();
  </script>
</body>
</html>`);
});

server.listen(PORT, '0.0.0.0', () => {
  console.log(`Demash Pools Web & APK Server running on port ${PORT}`);
});
