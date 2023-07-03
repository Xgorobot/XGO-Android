package com.luwu.xgo_robot.mActivity;

import static com.luwu.xgo_robot.mMothed.PublicMethod.hideBottomUIMenu;

import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.luwu.xgo_robot.R;
import com.luwu.xgo_robot.mMothed.PublicMethod;

public class PrivacyActivity extends AppCompatActivity {
    private ImageButton privacyBtnBack;
    private TextView privacyHtmlText;
    private TextView privacyHtmlTextTitle;
    protected final static String HTML_TEXT_TITLE="<h1 align=\"center\">隐私政策</h1>";
    protected final static String HTML_TEXT_TITLE_EN="<h1 align=\"center\">PRIVACY POLICY</h1>";
    protected final static String HTML_TEXT=
//            "<p>&nbsp;&nbsp;&nbsp;&nbsp;本隐私政策详细说明了哈尔滨陆吾智能科技有限公司（“陆吾智能”、“我们”或“我们的”）通过我们的应用程序收集的信息，以及我们将如何使用这些信息。</p>\n" +
//            "<h3>1.陆吾智能不会通过我们的应用程序收集用户的信息</h3>\n" +
//            "<p>&nbsp;&nbsp;&nbsp;&nbsp;我们深知用户个人信息的重要性，在我们的APP中，不要求用户输入任何个人信息资料。用户使用我们的软件时可能会被读取一些信息（比如基于GPS的粗略的位置信息、手机蓝牙状态、手机传感器状态等），这是由于APP需要和陆吾智能的机器人进行通讯，以便用户能够正常地使用我们的应用程序。陆吾智能承诺所读取到的任何信息，只被应用于APP的开发，而不会透露给第三方（其他个人或公司）。</p>\n" +
//            "<h3>2.手机APP可能会申请以下权限：</h3>\n" +
//            "<p>&nbsp;&nbsp;&nbsp;&nbsp;<b>· 手机储存权限：</b>用于应用下载安装、文件储存等核心功能</p>\n" +
//            "<p>&nbsp;&nbsp;&nbsp;&nbsp;<b>· 通过网络或卫星读取手机定位：</b>查找连接BLE蓝牙设备</p>\n" +
//            "<p>&nbsp;&nbsp;&nbsp;&nbsp;<b>· 开启蓝牙：</b>查找连接BLE蓝牙设备</p>\n" +
//            "<p>&nbsp;&nbsp;&nbsp;&nbsp;<b>· 读取手机振动信息：</b>与用户交互</p>\n" +
//            "<h3>3.APP的更新</h3>\n" +
//            "<p>&nbsp;&nbsp;&nbsp;&nbsp;为了保证我们的应用程序能够更好的为用户服务，陆吾智能有权不经过向您特别通知而对软件进行更新。</p>\n" +
//            "<h3>4.其他</h3>\n" +
//            "<p>&nbsp;&nbsp;&nbsp;&nbsp;安装并使用APP视为您同意上述隐私政策。</p>";
    "<p>哈尔滨陆吾智能科技有限公司是依据中华人民共和国法律成立的有限责任公司（以下简称“陆吾智能”或“我们”，包括其母公司、子公司、关联公司等）。我们重视维护和保护用户的个人信息。您在使用陆吾智能的产品或服务（包括网站 https://www.xgorobot.com/其子域名网站）时，我们可能会收集和使用您的个人信息。本《隐私权政策》旨在向您说明我们如何收集、储存、使用或分享您的个人信息。希望您仔细阅读本《隐私权政策》，以便在需要的时候，能让您作出合适的选择。</p>\n" +
            "<p>您使用或继续使用陆吾智能的产品或服务，即意味着您同意我们按照本《隐私权政策》收集、储存、使用和分享您的个人信息。</p>\n" +
            "<p>最近更新日期：2021年11月3日。</p>\n" +
            "<p>陆吾智能地址：哈尔滨市南岗区邮政街副434号哈工大科技园大厦二层203号</p>\n" +
            "<p>如果您有任何疑问、意见或建议，请通过以下联系方式与我们联系：</p>\n" +
            "<p>电子邮件：hello@xgorobot.com</p>\n" +
            "<p>本政策将帮助您了解以下内容：</p>\n" +
            "<p>1、我们如何收集和使用您的个人信息</p>\n" +
            "<p>2、我们如何使用Cookie和同类技术</p>\n" +
            "<p>3、我们如何共享、转让、公开披露您的个人信息</p>\n" +
            "<p>4、我们如何保护您的个人信息</p>\n" +
            "<p>5、您的权利</p>\n" +
            "<p>6、我们如何处理儿童的个人信息</p>\n" +
            "<p>7、您的个人信息如何在全球范围转移</p>\n" +
            "<p>8、本政策如何更新</p>\n" +
            "<p>9、如何联系我们</p>\n" +
            "<p>陆吾智能深知个人信息对您的重要性，并会尽全力保护您的个人信息安全可靠。我们致力于维持您对我们的信任，恪守以下原则，保护您的个人信息：权责一致原则、目的明确原则、选择同意原则、最少够用原则、确保安全原则、主体参与原则、公开透明原则等。同时，陆吾智能承诺，我们将按相应的安全标准，采取相应的安全保护措施来保护您的个人信息。</p>\n" +
            "<p>请在使用我们的产品(或服务)前，仔细阅读并了解本《隐私政策》。</p>\n" +
            "<h3>一、我们如何收集和使用您的个人信息</h3>\n" +
            "<p>个人信息是指以电子或者其他方式记录的能够单独或者与其他信息结合识别特定自然人身份或者反映特定自然人活动情况的各种信息。</p>\n" +
            "<p>陆吾智能仅会出于本政策所述的以下目的，收集和使用您的个人信息：</p>\n" +
            "<p>（一）为您提供软件编程服务</p>\n" +
            "<p>1. 您在注册账号时填写的信息。当您使用我们的产品或服务时，您可以直接通过邮箱登录，或者您可以向我们提供个人信息，其中包括您的用户名和密码，您还可以选择在自己的账号中添加邮箱、电话号码、第三方账号（微信和QQ）、学号注册登录网页或APP。</p>\n" +
            "<p>2. 您通过我们的客服或参加陆吾智能举办的活动时所提交的信息。您参与线上活动时填写的调查问卷中可能包含您的姓名、电话、电子邮箱、学生工号等。</p>\n" +
            "<p>3. 您通过其他方式向所提交的信息。当您使用我们的服务时，可以自愿填写昵称、性别、生日和所在地信息。可能会利用这些信息建立用户画像，以便为您提供更加准确和个性化的产品服务。</p>\n" +
            "<p>4. 操作日志信息。当您使用陆吾智能的线上产品或服务时，系统可能通过cookies或其他方式自动收集某些信息作为有关网络日志保存。收集此类信息的目的在于：通过数据统计改进和优化产品体验、保障服务稳定和网络安全。此类信息包含：您使用APP时的详细使用情况，您登录网页时的浏览信息，您的产品服务使用记录、网络状态、蓝牙权限、使用语言、访问日期和时间、位置权限、存储权限、语音权限、图像获取。</p>\n" +
            "<p>（二）为您推送和展示定制内容</p>\n" +
            "<p>基于收集到的信息，我们可以向您推荐您可能感兴趣的内容，包括但不限于向您推荐、展示您的编程内容，用户问卷调查，个性化广告等。</p>\n" +
            "<p>（三）持续开发和优化产品服务</p>\n" +
            "<p>我们使用收集到的信息保障我们的业务运营，例如评估、维护和改进产品和服务的性能，当软件系统发生故障时，陆吾智能开放平台会记录和分析系统故障时产生的信息，优化平台的相关服务。为迭代和开发新的产品和服务，我们可能对收集的信息加以统计分析辅助决策，但形成的数据分析结果不包含您的任何身份识别信息。当我们要将信息用于本政策未载明的其它用途时，会事先征求您的同意。当我们要将基于特定目的收集而来的信息用于其他目的时，会事先征求您的同意。</p>\n" +
            "<h3>二、我们如何使用Cookie和同类技术</h3>\n" +
            "<p>（一）Cookie</p>\n" +
            "<p>为确保软件运转，我们会在您的计算机或移动设备上存储名为Cookie的小数据文件。Cookie通常包含标识符、站点名称以及一些号码和字符。借助于Cookie，网站能够存储您的偏好等数据。我们不会将Cookie用于本政策所述目的之外的任何用途。</p>\n" +
            "<p>（二）网站信标和像素标签</p>\n" +
            "<p>除Cookie外，我们还会在网站和APP上使用网站信标和像素标签等其他同类技术。例如，我们向您发送的电子邮件可能含有链接至我们网站内容的点击URL。</p>\n" +
            "<p>（三）蓝牙和定位</p>\n" +
            "<p>蓝牙技术是一种无线数据和语音通信开放的全球规范，它是基于低成本的近距离无线连接，为固定和移动设备建立通信环境的一种特殊的近距离无线技术连接。 当你使用陆吾智能的产品或服务时，需要打开设备蓝牙和定位权限，连接产品。</p>\n" +
            "<h3>三、我们如何共享、转让、公开披露您的个人信息</h3>\n" +
            "<p>（一）共享</p>\n" +
            "<p>我们不会与陆吾智能以外的任何公司、组织和个人分享您的个人信息，</p>\n" +
            "<p>但以下情况除外：</p>\n" +
            "<p>1. 在获取明确同意的情况下共享：获得您的明确同意后，我们会与其他方共享您的个人信息。</p>\n" +
            "<p>2. 我们可能会根据法律法规规定，或按政府主管部门的强制性要求，对外共享您的个人信息。</p>\n" +
            "<p>3. 与我们的附属公司共享：您的个人信息可能会与陆吾智能的附属公司共享。我们只会共享必要的个人信息，且受本隐私政策中所声明目的的约束。附属公司如要改变个人信息的处理目的，将再次征求您的授权同意。</p>\n" +
            "<p>对我们与之共享个人信息的公司、组织和个人，我们会与其签署严格的保密协定，要求他们按照我们的说明、本隐私政策以及其他任何相关的保密和安全措施来处理个人信息。</p>\n" +
            "<p>（二）转让</p>\n" +
            "<p>我们不会将您的个人信息转让给任何公司、组织和个人，但以下情况除外：</p>\n" +
            "<p>1. 在获取明确同意的情况下转让：获得您的明确同意后，我们会向其他方转让您的个人信息；</p>\n" +
            "<p>2. 在涉及合并、收购或破产清算时，如涉及到个人信息转让，我们会在要求新的持有您个人信息的公司、组织继续受此隐私政策的约束，否则我们将要求该公司、组织重新向您征求授权同意。</p>\n" +
            "<p>（三）公开披露</p>\n" +
            "<p>我们仅会在以下情况下，公开披露您的个人信息：</p>\n" +
            "<p>1. 获得您明确同意后；</p>\n" +
            "<p>2. 基于法律的披露：在法律、法律程序、诉讼或政府主管部门强制性要求的情况下，我们可能会公开披露您的个人信息。</p>\n" +
            "<h3>四、我们如何保护您的个人信息</h3>\n" +
            "<p>1. 我们已使用安全防护措施保护您提供的个人信息，防止数据遭到未经授权访问、公开披露、使用、修改、损坏或丢失。我们会采取一切合理可行的措施，保护您的个人信息。例如，在您的浏览器与“服务”之间交换数据(如信用卡信息)时受SSL加密保护；我们同时对软件APP提供安全浏览方式；我们会使用加密技术确保数据的保密性；我们会使用受信赖的保护机制防止数据遭到恶意攻击；我们会部署访问控制机制，确保只有授权人员才可访问个人信息；以及我们会举办安全和隐私保护培训课程，加强员工对于保护个人信息重要性的认识。</p>\n" +
            "<p>2. 我们会采取一切合理可行的措施，确保未收集无关的个人信息。我们只会在达成本政策所述目的所需的期限内保留您的个人信息，除非需要延长保留期或受到法律的允许。</p>\n" +
            "<p>3. 互联网并非绝对安全的环境，而且电子邮件、即时通讯、及与其他APP用户的交流方式并未加密，我们强烈建议您不要通过此类方式发送个人信息。请使用复杂密码，协助我们保证您的账号安全。</p>\n" +
            "<p>4. 互联网环境并非百分之百安全，我们将尽力确保或担保您发送给我们的任何信息的安全性。我们提醒您知悉可能遭遇的互联网风险有：我们的物理、技术、或管理防护设施遭到破坏，可能导致信息被非授权访问、公开披露、篡改、或毁坏。</p>\n" +
            "<p>5. 在不幸发生个人信息安全事件后，我们将按照法律法规的要求，及时向您告知：安全事件的基本情况和可能的影响、我们已采取或将要采取的处置措施、您可自主防范和降低风险的建议、对您的补救措施等。我们将及时将事件相关情况以邮件、信函、电话、推送通知等方式告知您，难以逐一告知个人信息主体时，我们会采取合理、有效的方式发布公告。</p>\n" +
            "<p>同时，我们还将按照监管部门要求，主动上报个人信息安全事件的处置情况。</p>\n" +
            "<h3>五、您的权利</h3>\n" +
            "<p>按照中国相关的法律、法规、标准，以及其他国家、地区的通行做法，我们保障您对自己的个人信息行使以下权利：</p>\n" +
            "<p>（一）访问您的个人信息</p>\n" +
            "<p>您有权访问您的个人信息，法律法规规定的例外情况除外。如果您想行使数据访问权，可以通过以下方式自行访问：</p>\n" +
            "<p>账户信息——如果您希望访问或编辑您的账户中的个人资料信息、更改您的密码、添加安全信息或关闭您的账户等，您可以通过访问APP主页面执行此类操作。</p>\n" +
            "<p>对于您在使用我们的产品或服务过程中产生的其他个人信息，只要我们不需要过多投入，我们会向您提供。如果您想行使数据访问权，请发送电子邮件至hello@xgorobot.com。</p>\n" +
            "<p>（二）更正您的个人信息</p>\n" +
            "<p>当您发现我们处理的关于您的个人信息有错误时，您有权要求我们做出更正。您可以通过随时使用我们的Web表单联系，或发送电子邮件至hello@xgorobot.com提出更正申请。我们将在15天内回复您的更正请求。</p>\n" +
            "<p>（三）删除您的个人信息</p>\n" +
            "<p>在以下情形中，您可以向我们提出删除个人信息的请求：</p>\n" +
            "<p>1. 如果我们处理个人信息的行为违反法律法规；</p>\n" +
            "<p>2. 如果我们收集、使用您的个人信息，却未征得您的同意；</p>\n" +
            "<p>3. 如果我们处理个人信息的行为违反了与您的约定；</p>\n" +
            "<p>4. 如果您不再使用我们的产品或服务，或您注销了账号；</p>\n" +
            "<p>5. 如果我们不再为您提供产品或服务。</p>\n" +
            "<p>若我们决定响应您的删除请求，我们还将同时通知从我们获得您的个人信息的实体，要求其及时删除，除非法律法规另有规定，或这些实体获得您的独立授权。</p>\n" +
            "<p>当您从我们的服务中删除信息后，我们可能不会立即备份系统中删除相应的信息，但会在备份更新时删除这些信息。</p>\n" +
            "<p>（四）改变您授权同意的范围</p>\n" +
            "<p>每个业务功能需要一些基本的个人信息才能得以完成(见本政策“第一部分”)。对于额外收集的个人信息的收集和使用，您可以随时给予或收回您的授权同意。您可以通过发送信息至邮箱hello@xgorobot.com告知您的授权。</p>\n" +
            "<p>当您收回同意后，我们将不再处理相应的个人信息。但您收回同意的决定，不会影响此前基于您的授权而开展的个人信息处理。</p>\n" +
            "<p>（五）个人信息主体获取个人信息副本</p>\n" +
            "<p>您有权获取您的个人信息副本，您可以通过电子邮箱hello@xgorobot.com获取您的信息。</p>\n" +
            "<p>（六）约束信息系统自动决策</p>\n" +
            "<p>在某些业务功能中，我们可能仅依据信息系统、算法等在内的非人工自动决策机制做出决定。如果这些决定显著影响您的合法权益，您有权要求我们做出解释，我们也将提供适当的救济方式。</p>\n" +
            "<p>（七）响应您的上述请求</p>\n" +
            "<p>为保障安全，您可能需要提供书面请求，或以其他方式证明您的身份。我们可能会先要求您验证自己的身份，然后再处理您的请求。</p>\n" +
            "<p>我们将在15天内做出答复。如您不满意，还可以通过以下途径投诉：发送邮件至hello@xgorobot.com。</p>\n" +
            "<p>对于您合理的请求，我们不收取费用。对于那些无端重复、需要过多技术手段(例如，需要开发新系统或从根本上改变现行惯例)、给他人合法权益带来风险或者非常不切实际(例如，涉及备份磁带上存放的信息)的请求，我们可能会予以拒绝。</p>\n" +
            "<p>在以下情形中，按照法律法规要求，我们将无法响应您的请求：</p>\n" +
            "<p>1. 与国家安全、国防安全直接相关的；</p>\n" +
            "<p>2. 与公共安全、公共卫生、重大公共利益直接相关的；</p>\n" +
            "<p>3. 与犯罪侦查、起诉、审判和判决执行等直接相关的；</p>\n" +
            "<p>4. 有充分证据表明您存在主观恶意或滥用权利的；</p>\n" +
            "<p>5. 响应您的请求将导致您或其他个人、组织的合法权益受到严重损害的。</p>\n" +
            "<p>6. 涉及商业秘密的。</p>\n" +
            "<h3>六、我们如何处理儿童的个人信息</h3>\n" +
            "<p>我们的产品、网站和服务同时面向学生。如果没有父母或监护人的同意，儿童不得创建自己的用户账户。</p>\n" +
            "<p>对于经父母同意而收集儿童个人信息的情况，我们只会在受到法律允许、父母或监护人明确同意或者保护儿童所必要的情况下使用或公开披露此信息。</p>\n" +
            "<p>尽管当地法律和习俗对儿童的定义不同，但我们将不满14周岁的任何人均视为儿童。</p>\n" +
            "<p>如果我们发现自己在未事先获得可证实的父母同意的情况下收集了儿童的个人信息，则会尽快删除相关数据。</p>\n" +
            "<h3>七、您的个人信息如何在全球范围转移</h3>\n" +
            "<p>原则上，我们在中华人民共和国境内收集和产生的个人信息，将存储在中华人民共和国境内。</p>\n" +
            "<p>由于我们通过遍布全球的资源和服务器提供产品或服务，这意味着，在获得您的授权同意后，您的个人信息可能会被转移到您使用产品或服务所在国家／地区的境外管辖区，或者受到来自这些管辖区的访问。</p>\n" +
            "<p>此类管辖区可能设有不同的数据保护法，甚至未设立相关法律。在此类情况下，我们会确保您的个人信息得到在中华人民共和国境内足够同等的保护。例如，我们会请求您对跨境转移个人信息的同意，或者在跨境数据转移之前实施数据去标识化等安全举措。</p>\n" +
            "<h3>八、本政策如何更新</h3>\n" +
            "<p>我们的隐私政策可能变更。</p>\n" +
            "<p>未经您明确同意，我们不会削减您按照本隐私政策所应享有的权利。我们会在本页面上发布对本政策所做的任何变更。</p>\n" +
            "<p>对于重大变更，我们还会提供更为显著的通知(包括对于某些服务，我们会通过电子邮件发送通知，说明隐私政策的具体变更内容)。</p>\n" +
            "<p>本政策所指的重大变更包括但不限于：</p>\n" +
            "<p>1. 我们的服务模式发生重大变化。如处理个人信息的目的、处理的个人信息类型、个人信息的使用方式等；</p>\n" +
            "<p>2. 我们在所有权结构、组织架构等方面发生重大变化。如业务调整、破产并购等引起的所有者变更等；</p>\n" +
            "<p>3. 个人信息共享、转让或公开披露的主要对象发生变化；</p>\n" +
            "<p>4. 您参与个人信息处理方面的权利及其行使方式发生重大变化；</p>\n" +
            "<p>5. 我们负责处理个人信息安全的责任部门、联络方式及投诉渠道发生变化时；</p>\n" +
            "<p>6. 个人信息安全影响评估报告表明存在高风险时。</p>\n" +
            "<p>我们还会将本政策的旧版本存档，供您查阅。</p>\n" +
            "<h3>九、如何联系我们</h3>\n" +
            "<p>如果您对本隐私政策有任何疑问、意见或建议，通过邮件方式与我们联系：hello@xgorobot.com。</p>\n" +
            "<p>我们设立了个人信息保护专职部门(或个人信息保护专员)，您可以通过以下方式与其联系：hello@xgorobot.com。</p>\n" +
            "<p>如果您对我们的回复不满意，特别是我们的个人信息处理行为损害了您的合法权益，您还可以通过以下外部途径寻求解决方案：向【陆吾智能住所地有管辖权的法院提起诉讼】。</p>";

    protected final static String HTML_TEXT_EN =
            "<p>Harbin Luwu Intelligent Technology Co., Ltd. (“LuWu Intelligent” or “we,” including its subsidiaries, parent company and affiliates) is a limited liability company established in accordance with the laws of the People's Republic of China. We value the protection and privacy of our users' personal information. When you use LuWu Intelligent's products or services (including the website https://www.xgorobot.com/and its subdomains), we may collect and use your personal information. This Privacy Policy aims to explain how we collect, store, use, and share your personal information. We hope that you carefully read this Privacy Policy so that you can make appropriate choices when needed.</p>\n" +
                    "<p>Your use or continued use of LuWu Intelligent's products or services implies that you agree to the collection, storage, use, and sharing of your personal information in accordance with this Privacy Policy.</p>\n" +
                    "<p>Last Updated: November 3, 2021.</p>\n" +
                    "<p>Address of LuWu Intelligent: Room 203, 2nd Floor, Harbin Institute of Technology Science Park Building, No. 434 Postal Street, Nangang District, Harbin City</p>\n" +
                    "<p>If you have any questions, comments, or suggestions, please contact us through the following:</p>\n" +
                    "<p>Email: hello@xgorobot.com</p>\n" +
                    "<p>This policy will help you understand the following:</p>\n" +
                    "<p>1. How we collect and use your personal information</p>\n" +
                    "<p>2. How we use cookies and similar technologies</p>\n" +
                    "<p>3. How we share, transfer, and disclose your personal information</p>\n" +
                    "<p>4. How we protect your personal information</p>\n" +
                    "<p>5. Your rights</p>\n" +
                    "<p>6. How we handle children's personal information</p>\n" +
                    "<p>7. How your personal information is transferred globally</p>\n" +
                    "<p>8. How this policy is updated</p>\n" +
                    "<p>9. How to contact us</p>\n" +
                    "<p>LuWu Intelligent recognizes the importance of personal information to you, and will do its best to protect the security and reliability of your personal information. We are committed to maintaining your trust in us and adhering to the following principles to protect your personal information: principle of consistent responsibility, clear purpose principle, choice and consent principle, minimum necessary principle, ensuring security principle, principle of subject participation, and principle of transparency. At the same time, LuWu Intelligent promises that we will adopt appropriate security protection measures according to the corresponding security standards to protect your personal information.</p>\n" +
                    "<p>Please read and understand this Privacy Policy carefully before using our products (or services).</p>\n" +
                    "<h3>I. How we collect and use your personal information</h3>\n" +
                    "<p>Personal information refers to various information recorded electronically or by other means that can identify a specific natural person's identity or reflect specific natural person activities, either individually or in combination with other information.</p>\n" +
                    "<p>LuWu Intelligent only collects and uses your personal information for the following purposes as described in this policy:</p>\n" +
                    "<p>(I) To provide you with software programming services</p>\n" +
                    "<p>1.  Information you provided when registering for an account. When you use our products or services, you can log in directly through email, or you can provide us with personal information, including your username and password. You can also choose to add your email, phone number, third-party accounts (WeChat and QQ), or student ID to register and log in to the website or app.</p>\n" +
                    "<p>2.  Information you submitted when contacting our customer service or participating in activities held by LuWu Intelligent. The survey questionnaire that you filled out when participating in online activities may include your name, phone number, email address, and student ID.</p>\n" +
                    "<p>3.  Information you submitted through other means. When you use our services, you can voluntarily fill in nickname, gender, birthday, and location information. This information may be used to build user profiles so that we can provide you with more accurate and personalized product services.</p>\n" +
                    "<p>4.  Operation log information. When you use Landasoft's online products or services, the system may automatically collect certain information as network log storage through cookies or other means. The purpose of collecting such information is to improve and optimize the product experience through data statistics, ensure service stability and network security. Such information includes: detailed usage when you use the app, browsing information when you log in to the website, your product service usage records, network status, Bluetooth permissions, language used, access date and time, location permissions, storage permissions, voice permissions, image acquisition.</p>\n" +
                    "<p>(II) Push and display customized content for you</p>\n" +
                    "<p>Based on the collected information, we can recommend content that you may be interested in, including but not limited to recommending and displaying your programming content, user questionnaires, personalized advertisements, etc.</p>\n" +
                    "<p>(III) Continuous development and optimization of product services</p>\n" +
                    "<p>We use the collected information to ensure our business operations, such as evaluating, maintaining and improving the performance of products and services. When a software system fails, the Landasoft open platform will record and analyze the information generated during the system failure to optimize relevant services of the platform. For iteration and development of new products and services, we may use the collected information to conduct statistical analysis to assist in decision-making, but the resulting data analysis results do not contain any identifying information about you. When we want to use the information for other purposes not stated in this policy, we will seek your consent in advance. When we want to use the information collected for specific purposes for other purposes, we will seek your consent in advance.</p>\n" +
                    "<h3>II. How we use Cookies and Similar Technologies</h3>\n" +
                    "<p>(I) Cookies</p>\n" +
                    "<p>To ensure the operation of the software, we will store small data files called cookies on your computer or mobile device. Cookies usually contain identifiers, site names, and some numbers and characters. With the help of cookies, websites can store data such as your preferences. We will not use cookies for any purpose other than those stated in this policy.</p>\n" +
                    "<p>(II) Website beacons and pixel tags</p>\n" +
                    "<p>In addition to cookies, we also use website beacons and pixel tags and other similar technologies on websites and apps. For example, emails we send to you may contain click-through URLs linking to content on our website.</p>\n" +
                    "<p>(III) Bluetooth and positioning</p>\n" +
                    "<p>Bluetooth technology is a global standard for wireless data and voice communication that is open and low-cost, and is a special short-range wireless technology connection based on low-cost fixed and mobile devices to establish a communication environment. When you use Landasoft's products or services, you need to turn on device Bluetooth and location permissions to connect to the product.</p>\n" +
                    "<h3>III. How we share, transfer, and publicly disclose your personal information</h3>\n" +
                    "<p>(I) Sharing</p>\n" +
                    "<p>We will not share your personal information with any company, organization or individual outside of Landasoft,</p>\n" +
                    "<p>except in the following situations:</p>\n" +
                    "<p>1.  Sharing with explicit consent: After obtaining your explicit consent, we will share your personal information with other parties.</p>\n" +
                    "<p>2.  We may share your personal information with external parties in accordance with legal regulations or mandatory requirements from government regulatory agencies.</p>\n" +
                    "<p>3.  Sharing with our affiliates: Your personal information may be shared with Landasoft's affiliates. We will only share the necessary personal information and are bound by the purposes declared in this privacy policy. If an affiliate company wants to change the purpose of processing personal information, your authorized consent will be sought again.</p>\n"+
                    "<p>For companies, organizations, and individuals with whom we share personal information, we will sign strict confidentiality agreements with them, requiring them to handle personal information in accordance with our instructions, this privacy policy, and any other relevant confidentiality and security measures.</p>\n" +
                    "<p>(II) Transfer</p>\n" +
                    "<p>We will not transfer your personal information to any company, organization, or individual, except for the following:</p>\n" +
                    "<p>1.  Transferred with explicit consent: After obtaining your explicit consent, we will transfer your personal information to other parties;</p>\n" +
                    "<p>2.  In the event of a merger, acquisition, or bankruptcy liquidation, if it involves the transfer of personal information, we will require the new company or organization that holds your personal information to continue to be bound by this privacy policy. Otherwise, we will ask the company or organization to obtain your authorization again.</p>\n" +
                    "<p>(III) Public Disclosure</p>\n" +
                    "<p>We will only disclose your personal information in the following circumstances:</p>\n" +
                    "<p>1.  After obtaining your explicit consent;</p>\n" +
                    "<p>2.  Based on legal disclosure: In the event of a mandatory request from law, legal procedures, litigation, or government authorities, we may publicly disclose your personal information.</p>\n" +
                    "<h3>IV. How We Protect Your Personal Information</h3>\n" +
                    "<p>1.  We have used security measures to protect the personal information you provide us to prevent unauthorized access, public disclosure, use, modification, damage, or loss of data. We will take all reasonable and feasible measures to protect your personal information. For example, when exchanging data (such as credit card information) between your browser and the \"Service,\" it is protected by SSL encryption; we also provide a secure browsing mode for the software APP; we use encryption technology to ensure data confidentiality; we use trusted protection mechanisms to prevent malicious attacks on data; we deploy access control mechanisms to ensure that only authorized personnel can access personal information; and we hold security and privacy protection training courses to enhance employee awareness of the importance of protecting personal information.</p>\n" +
                    "<p>2.  We will take all reasonable and feasible measures to ensure that irrelevant personal information is not collected. We will only retain your personal information for the period necessary to achieve the purposes stated in this policy, unless it is necessary to extend the retention period or permitted by law.</p>\n" +
                    "<p>3.  The Internet is not an absolutely secure environment, and email, instant messaging, and communication with other APP users are not encrypted. We strongly recommend that you do not send personal information through such means. Please use complex passwords to help us ensure the security of your account.</p>\n" +
                    "<p>4.  The Internet environment is not 100% secure, and we will do our best to ensure or guarantee the security of any information you send to us. We remind you that possible Internet risks may include: damage to our physical, technical, or managerial protection facilities, which may result in unauthorized access, public disclosure, modification, or destruction of information.</p>\n" +
                    "<p>5.  In the event of a personal information security incident, we will promptly inform you in accordance with legal requirements: the basic situation and possible impact of the security incident, the disposal measures we have taken or will take, your suggestions for self-prevention and risk reduction, and your remedies. We will inform you of the relevant information of the event through email, letter, telephone, push notification, etc. When it is difficult to inform each individual data subject one by one, we will adopt reasonable and effective methods to publish announcements.</p>\n" +
                    "<p>At the same time, we will actively report the disposal of personal information security incidents according to regulatory requirements.</p>\n" +
                    "<h3>V. Your Rights</h3>\n" +
                    "<p>In accordance with relevant Chinese laws, regulations, standards, and common practices in other countries and regions, we guarantee your right to exercise the following rights regarding your personal information:</p>\n" +
                    "<p>(I) Access your personal information</p>\n" +
                    "<p>You have the right to access your personal information, except as provided by laws and regulations. If you want to exercise your data access rights, you can access them on the APP's main page by editing your account information, changing your password, adding security information, or closing your account.</p>\n" +
                    "<p>For other personal information generated during your use of our products or services, we will provide it to you as long as it does not require too much input from us. If you want to exercise your data access rights, please send an email to hello@xgorobot.com.</p>\n" +
                    "<p>(II) Correct your personal information</p>\n" +
                    "<p>If you find that the personal information we process about you is incorrect, you have the right to request correction. You can contact us at any time using our web form or send an email to hello@xgorobot.com to request a correction. We will respond to your correction request within 15 days.</p>\n" +
                    "<p>(III) Delete your personal information</p>\n" +
                    "<p>You can request that we delete your personal information in the following circumstances:</p>\n" +
                    "<p>1.  If our handling of personal information violates laws and regulations;</p>\n" +
                    "<p>2.  If we collect and use your personal information without obtaining your consent;</p>\n" +
                    "<p>3.  If our processing of personal information violates our agreement with you;</p>\n" +
                    "<p>4.  If you no longer use our products or services, or if you cancel your account;</p>\n" +
                    "<p>5.  If we no longer provide products or services to you.</p>\n" +
                    "<p>If we decide to respond to your deletion request, we will also notify entities that obtained your personal information from us to timely delete it, unless otherwise provided by laws and regulations or these entities obtained your independent authorization.</p>\n" +
                    "<p>After you delete information from our service, we may not immediately delete the corresponding information from our backup system, but we will delete it when the backup is updated.</p>\n" +
                    "<p>(IV) Change the scope of your authorized consent</p>\n" +
                    "<p>Each business function requires some basic personal information to be completed (see \"Part I\" of this policy). For additional personal information collected and used, you can give or withdraw your consent at any time. You can inform us of your authorization by sending a message to hello@xgorobot.com.</p>\n" +
                    "<p>Once you withdraw your consent, we will no longer process the corresponding personal information. However, your decision to withdraw consent does not affect personal information processing based on your authorization before such withdrawal.</p>\n" +
                    "<p>(V) Personal information subject obtains personal information copy</p>\n" +
                    "<p>You have the right to obtain a copy of your personal information. You can obtain your information by emailing hello@xgorobot.com.</p>\n" +
                    "<p>(VI) Restrict information system automatic decision-making</p>\n" +
                    "<p>In certain business functions, we may only make decisions based on non-human automated decision-making mechanisms such as information systems and algorithms. If these decisions significantly affect your legitimate rights and interests, you have the right to request an explanation from us, and we will also provide appropriate remedies.</p>\n" +
                    "<p>(VII) Respond to your above requests</p>\n" +
                    "<p>To ensure security, you may need to provide a written request or other proof of your identity. We may first ask you to verify your identity before processing your request.</p>\n" +
                    "<p>We will respond within 15 days. If you are not satisfied, you can also complain by sending an email to hello@xgorobot.com.</p>\n" +
                    "<p>For your reasonable requests, we do not charge any fees. For requests that are groundlessly repetitive, require too much technical means (such as the need to develop new systems or fundamentally change current practices), pose risks to the legitimate rights and interests of others, or are highly impractical (such as involving information stored on backup tapes), we may refuse.</p>\n" +
                    "<p>In the following circumstances, we will be unable to respond to your request as required by laws and regulations:</p>\n" +
                    "<p>1.  Directly related to national security or national defense security;</p>\n" +
                    "<p>2.  Directly related to public safety, public health, and major public interests;</p>\n" +
                    "<p>3.  Directly related to criminal investigation, prosecution, trial, and enforcement of judgments;</p>\n" +
                    "<p>4.  There is sufficient evidence to show that you have subjective malice or abuse of rights;</p>\n" +
                    "<p>5.  Responding to your request will cause serious damage to your or other individuals' or organizations' legitimate rights and interests; </p>\n" +
                    "<p>6.  Involving trade secrets.</p>\n" +
                    "<h3>VI. How We Handle Children's Personal Information</h3>\n" +
                    "<p>Our products, websites, and services are also designed for students. Children cannot create their own user accounts without the consent of their parents or guardians.</p>\n" +
                    "<p>For cases where children's personal information is collected with the consent of parents, we will only use or disclose this information when permitted by law, when explicitly agreed upon by parents or guardians, or when necessary to protect children.</p>\n" +
                    "<p>Although the definitions of \"children\" vary under local laws and customs, we will treat anyone under the age of 14 as a child.</p>\n" +
                    "<p>If we find that we have collected children's personal information without prior verifiable parental consent, we will delete relevant data as soon as possible.</p>\n" +
                    "<h3>VII. How Your Personal Information Is Transferred Globally</h3>\n" +
                    "<p>In principle, we collect and generate personal information within the territory of the People's Republic of China and store it within the territory of the People's Republic of China.</p>\n" +
                    "<p>Because we provide products or services through resources and servers around the world, this means that with your authorized consent, your personal information may be transferred to overseas jurisdictions where you use our products or services or may be accessed by these jurisdictions.</p>\n" +
                    "<p>Such jurisdictions may have different data protection laws or even no relevant laws. In such cases, we will ensure that your personal information receives sufficient and equivalent protection within the territory of the People's Republic of China. For example, we may request your consent for cross-border transfer of personal information or implement security measures such as data de-identification before cross-border data transfers occur.</p>\n" +
                    "<h3>VIII. How This Policy is Updated</h3>\n" +
                    "<p>Our privacy policy may change.</p>\n" +
                    "<p>We will not reduce your rights under this privacy policy without your explicit consent. We will publish any changes to this policy on this page.</p>\n" +
                    "<p>For significant changes, we will also provide more prominent notice (including for certain services, we will send an email notification stating the specific changes to the privacy policy).</p>\n" +
                    "<p>Significant changes referred to in this policy include but are not limited to:</p>\n" +
                    "<p>1.  Our service model undergoes significant changes, such as the purpose of personal information processing, types of personal information processed, and methods of using personal information;</p>\n" +
                    "<p>2.  We undergo significant changes in ownership structure, organizational structure, etc., such as business adjustments, bankruptcy mergers, and owner changes caused by them;</p>\n" +
                    "<p>3.  The main objects of personal information sharing, transfer or public disclosure change;</p>\n" +
                    "<p>4.  Your participation rights in personal information processing and their exercise methods undergo significant changes;</p>\n" +
                    "<p>5.  When the department responsible for personal information security, contact information, and complaint channels change;</p>\n" +
                    "<p>6.  When the personal information security impact assessment report shows high risks.</p>\n" +
                    "<p>We will also archive previous versions of this policy for your reference.</p>\n" +
                    "<h3>IX. How to Contact Us</h3>\n" +
                    "<p>If you have any questions, opinions, or suggestions about this privacy policy, please contact us by email: hello@xgorobot.com.</p>\n" +
                    "<p>We have established a dedicated department (or personal information protection officer) for personal information protection, and you can contact them through the following methods: hello@xgorobot.com.</p>\n" +
                    "<p>If you are not satisfied with our response, especially if our personal information processing activities have harmed your legitimate rights and interests, you can seek a solution through external means: file a lawsuit with the court having jurisdiction over XGORobot's domicile.</p>";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy_policy);

        privacyBtnBack = findViewById(R.id.privacyBtnBack);
        privacyBtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        privacyHtmlTextTitle = findViewById(R.id.privacyHtmlTextTitle);
        privacyHtmlText = findViewById(R.id.privacyHtmlText);
        switch(PublicMethod.localeLanguage){
            case "zh":
                privacyHtmlTextTitle.setText(Html.fromHtml(HTML_TEXT_TITLE));
                privacyHtmlText.setText(Html.fromHtml(HTML_TEXT));
                break;
            default:
                privacyHtmlTextTitle.setText(Html.fromHtml(HTML_TEXT_TITLE_EN));
                privacyHtmlText.setText(Html.fromHtml(HTML_TEXT_EN));
        }

    }

    protected void onResume() {
        super.onResume();
        hideBottomUIMenu(PrivacyActivity.this);
    }
}
