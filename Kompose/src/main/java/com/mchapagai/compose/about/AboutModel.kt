package com.mchapagai.compose.about

data class AboutModel(
    val title: String = "Open source licenses",
    val license: String = "The application uses following software or libraries",
    val softwareList: List<String> = listOf(
        "Discover Movies",
        "Source Sans Fonts",
        "Creative Commons (IconFinder)"
    ),
    val licenseList: List<String> = listOf(
        "MIT License <br>\n" +
                "        Copyright (c) 2018 Manorath Chapagai <br>\n" +
                "        Permission is hereby granted, free of charge, to any person obtaining a copy\n" +
                "        of this software and associated documentation files (the \"Software\"), to deal\n" +
                "        in the Software without restriction, including without limitation the rights\n" +
                "        to use, copy, modify, merge, publish, distribute, sublicense, and/or sell\n" +
                "        copies of the Software, and to permit persons to whom the Software is\n" +
                "        furnished to do so, subject to the following conditions:<br>\n" +
                "\n" +
                "        The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.<br>\n" +
                "        THE SOFTWARE IS PROVIDED \"AS IS\", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR\n" +
                "        IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,\n" +
                "        FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE\n" +
                "        AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER\n" +
                "        LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,\n" +
                "        OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.",
        "Copyright 2010, 2012, 2014 Adobe Systems Incorporated (http://www.adobe.com/), with Reserved Font Name ‘Source’.\n" +
                "        This Font Software is licensed under the SIL Open Font License, Version 1.1.\n" +
                "        This license is copied below, and is also available with a FAQ at:\n" +
                "        http://scripts.sil.org/OFL (https://fonts.google.com/specimen/Source+Sans+Pro)",
        "CREATIVE COMMONS CORPORATION IS NOT A LAW FIRM AND DOES NOT PROVIDE LEGAL SERVICES. DISTRIBUTION OF THIS LICENSE DOES NOT CREATE AN ATTORNEY-CLIENT RELATIONSHIP. CREATIVE COMMONS PROVIDES THIS INFORMATION ON AN \"AS-IS\" BASIS. CREATIVE COMMONS MAKES NO WARRANTIES REGARDING THE INFORMATION PROVIDED, AND DISCLAIMS LIABILITY FOR DAMAGES RESULTING FROM ITS USE.<br> \n" +
                "        https://creativecommons.org/licenses/by/3.0/legalcode"
    )
)
