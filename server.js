center; width: 100%; height: 44px; background: linear-gradient(135deg, #0284c7 0%, #10b981 100%); color: #fff; text-decoration: none; font-size: 12px; font-weight: 700; text-transform: uppercase; border-radius: 8px; } .footer { font-size: 10px; color: var(--text-secondary); text-align: center; font-family: monospace; margin-top: 48px; } </style> </head> <body> <div class="container"> <div class="brand-header"> <h1 class="brand-title">Demash Global</h1> </div> <div>${catalogHTML}</div> <div class="footer"> Workshop Base: ${SYSTEM_CONFIG.NORTON_BASE}.<br> Global Operations Automated • Secure Routing Active. </div> </div> </body> </html>);
});
app.use((err, req, res, next) => { res.status(200).send("Sovereign Protection Active."); });
app.listen(SYSTEM_CONFIG.PORT);

